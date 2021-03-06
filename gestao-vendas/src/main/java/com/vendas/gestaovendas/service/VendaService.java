package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaRequestDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaRequestDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ItemVendaRepository;
import com.vendas.gestaovendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService extends AbstractVendaService {

    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;

    @Autowired
    public VendaService(ClienteService clienteService, ProdutoService produtoService,
                        VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository) {
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    // Lista as vendas de um cliente
    public ClienteVendaResponseDTO listarVendasPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDTOList =
                vendaRepository.findByClienteCodigo(codigoCliente)
                        .stream()
                        .map(venda -> criandoVendaResponseDTO(venda,
                                      itemVendaRepository.buscarPorCodigo(venda.getCodigo())))
                        .collect(Collectors.toList());
        return  new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOList);
    }

    // Lista uma venda pelo c??digo da venda
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepository.buscarPorCodigo(venda.getCodigo());
        return retornandoClienteVendaResponseDTO(venda, itensVenda);
    }

    // Salva uma Venda
    // Anotacao  @Transactional inclusa para validar qualquer exception existente na transacao
    // parametros = somente leitura false (readOnly) e rollback informando a "Exception" mais alta caso
    // tenha algum erro ao salvar os dados
    // Quando existe salvamento em mais de uma tabela ?? aconselhado utilizar essa anotacao
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaRequestDTO) {
        // Valida se o cliente existe na base de Dados
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        // Valida se o produto existe e atualiza a quantidade em estoque
        validarProdutoExisteEAtualizarQuantidade(vendaRequestDTO.getItensVendaRequestDTO());

        Venda vendaSalva = salvarVenda(cliente, vendaRequestDTO);

        return retornandoClienteVendaResponseDTO(vendaSalva,
                itemVendaRepository.buscarPorCodigo(vendaSalva.getCodigo()));
    }

    // M??todo para Atualizar uma venda
    // Anotacao  @Transactional inclusa para validar qualquer exception existente na transacao
    // parametros = somente leitura false (readOnly) e rollback informando a "Exception" mais alta caso
    // tenha algum erro ao atualizar os dados
    // Quando existe salvamento em mais de uma tabela ?? aconselhado utilizar essa anotacao
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO atualizar(Long codigoVenda,  Long codigoCliente, VendaRequestDTO vendaRequestDTO) {
        // Valida se a Venda existe no banco de dados
        validarVendaExiste(codigoVenda);
        // Validar se Cliente Existe na base de dados
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        // Busca os itens da venda que est??o na base de dados
        List<ItemVenda> itemVendasList = itemVendaRepository.buscarPorCodigo(codigoVenda);
        // Valida se o produto da lista existe e devolve a quantidade em estoque
        validarProdutoExisteEDevolverEstoque(itemVendasList);
        // Valida a quantidade do Produto em estoque
        validarProdutoExisteEAtualizarQuantidade(vendaRequestDTO.getItensVendaRequestDTO());
        // Deletar os itens da venda anterior para atualizar com os novos vindos da request
        itemVendaRepository.deleteAll(itemVendasList);
        // Atualiza a venda com os valores novos
        Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaRequestDTO);

        return retornandoClienteVendaResponseDTO(vendaAtualizada,
                itemVendaRepository.buscarPorCodigo(vendaAtualizada.getCodigo()));
    }

    // M??todo para deletar uma venda
    // Anotacao  @Transactional inclusa para validar qualquer exception existente na transacao
    // parametros = somente leitura false (readOnly) e rollback informando a "Exception" mais alta caso
    // tenha algum erro ao deletar os dados
    // Quando existe salvamento em mais de uma tabela ?? aconselhado utilizar essa anotacao
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda) {
        // Valida se a Venda existe no banco de dados
        validarVendaExiste(codigoVenda);
        // Busca os Itens da venda
        List<ItemVenda> itemVendas = itemVendaRepository.buscarPorCodigo(codigoVenda);
        // Valida se o Produto existe e atualiza quantidade do produto em estoque
        validarProdutoExisteEDevolverEstoque(itemVendas);
        // Deleta os Itens da venda
        itemVendaRepository.deleteAll(itemVendas);
        // Deleta a Venda
        vendaRepository.deleteById(codigoVenda);
    }

    private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda) {
        itensVenda.forEach(itemVenda -> {
            Produto produto = produtoService.validarSeProdutoExiste(itemVenda.getProduto().getCodigo());
            // Atualizando quantidade disponivel em estoque
            produto.setQuantidade(produto.getQuantidade() + itemVenda.getQuantidade());
            // Persistir no Banco de dados a quantidade do produto no estoque
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaRequestDTO) {
        Venda vendaSalva = vendaRepository.save(new Venda(vendaRequestDTO.getData(), cliente));
        vendaRequestDTO.getItensVendaRequestDTO()
                .stream()
                .map(itemVendaRequestDTO -> criandoItemVenda(itemVendaRequestDTO, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaRequestDTO) {
        Venda vendaSalva = vendaRepository.save(new Venda(codigoVenda, vendaRequestDTO.getData(), cliente));
        vendaRequestDTO.getItensVendaRequestDTO()
                .stream()
                .map(itemVendaRequestDTO -> criandoItemVenda(itemVendaRequestDTO, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private void validarProdutoExisteEAtualizarQuantidade(List<ItemVendaRequestDTO> itensVendaRequestDTO) {
        itensVendaRequestDTO.forEach(item -> {
            Produto produto = produtoService.validarSeProdutoExiste(item.getCodigoProduto());
            // Valida se a quatidade esta disponivel em estoque
            validarSeQuantidadeProdutoExiste(produto, item.getQuantidade());
            // Atualizando quantidade disponivel em estoque
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            // Persistir no Banco de dados a quantidade do produto no estoque
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private void validarSeQuantidadeProdutoExiste(Produto produto, Integer qtdeVendaDto) {
        if(!(produto.getQuantidade() >= qtdeVendaDto)) {
            throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s "
                    + "nao esta disponivel em estoque", qtdeVendaDto, produto.getDescricao()));
        }
    }

    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepository.findById(codigoVenda);
        if(venda.isEmpty()) {
            throw new RegraNegocioException(String.format("Venda de codigo %s n??o encontrada", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigoCliente);
        if(cliente.isEmpty()) {
            throw new RegraNegocioException(String.format("O Cliente de c??digo %s nao esta cadastrado", codigoCliente));
        }
        return cliente.get();
    }
}
