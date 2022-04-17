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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService extends AbstractVendaService {

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
                        .map(venda -> criandoVendaResponseDTO(venda,
                                      itemVendaRepository.findByVendaCodigo(venda.getCodigo())))
                        .collect(Collectors.toList());
        return  new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOList);
    }

    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepository.findByVendaCodigo(venda.getCodigo());
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(),
                Arrays.asList(criandoVendaResponseDTO(venda, itensVenda)));
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
