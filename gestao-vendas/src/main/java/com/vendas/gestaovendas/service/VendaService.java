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

    // Lista uma venda pelo código da venda
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepository.buscarPorCodigo(venda.getCodigo());
        return retornandoClienteVendaResponseDTO(venda, itensVenda);
    }

    // Salva uma Venda
    // Anotacao  @Transactional inclusa para validar qualquer exception existente na transacao
    // parametros = somente leitura false (readOnly) e rollback informando a "Exception" mais alta caso
    // tenha algum erro ao salvar os dados
    // Quando existe salvamento em mais de uma tabela é aconselhado utilizar essa anotacao
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaRequestDTO) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteEAtualizarQuantidade(vendaRequestDTO.getItensVendaRequestDTO());
        Venda vendaSalva = salvarVenda(cliente, vendaRequestDTO);

        return retornandoClienteVendaResponseDTO(vendaSalva,
                itemVendaRepository.buscarPorCodigo(vendaSalva.getCodigo()));
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaRequestDTO) {
        Venda vendaSalva = vendaRepository.save(new Venda(vendaRequestDTO.getData(), cliente));
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
            // Persistir no Banco de dados a quantidade do produto apos a venda
            produtoService.atualizarQuantidadeAposVenda(produto);
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
            throw new RegraNegocioException(String.format("Venda de codigo %s não encontrada", codigoVenda));
        }
        return venda.get();
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigoCliente);
        if(cliente.isEmpty()) {
            throw new RegraNegocioException(String.format("O Cliente de código %s nao esta cadastrado", codigoCliente));
        }
        return cliente.get();
    }
}
