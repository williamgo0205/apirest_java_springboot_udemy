package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Venda;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ItemVendaRepository;
import com.vendas.gestaovendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private ClienteService clienteService;
    private VendaRepository vendaRepository;
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    public VendaService(ClienteService clienteService, VendaRepository vendaRepository,
                        ItemVendaRepository itemVendaRepository) {
        this.clienteService = clienteService;
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    public ClienteVendaResponseDTO listarVendasPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDTOList =
                vendaRepository.findByClienteCodigo(codigoCliente)
                        .stream()
                        .map(this::criandoVendaResponseDTO)
                        .collect(Collectors.toList());
        return  new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOList);
    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigoCliente);
        if(cliente.isEmpty()) {
            throw new RegraNegocioException(String.format("O Cliente de código %s nao esta cadastrado", codigoCliente));
        }
        return cliente.get();
    }

    // Metodo de conversao da Venda para VendaResponseDTO
    private VendaResponseDTO criandoVendaResponseDTO(Venda venda) {
        // Utilizando Lambda para realizar a conversao "itemVendaRepository.findByVendaCodigo"
        // para os ItemVendaResponseDTO
        List<ItemVendaResponseDTO> itemVendaList = itemVendaRepository.findByVendaCodigo(venda.getCodigo())
                .stream()
                .map(this::criandoItensVendaResponseDTO)
                .collect(Collectors.toList());

        return  new VendaResponseDTO(venda.getCodigo(), venda.getData(), itemVendaList);
    }

    // Metodo de conversao da ItemVenda para ItemVendaResponseDTO
    private ItemVendaResponseDTO criandoItensVendaResponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(),
                itemVenda.getProduto().getDescricao());
    }
}
