package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.dto.venda.model.ItemVendaRequestDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaResponseDTO;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVendaService {

    // Metodo de conversao da Venda para VendaResponseDTO
    protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
        // Utilizando Lambda para realizar a conversao "itemVendaRepository.findByVendaCodigo"
        // para os ItemVendaResponseDTO
        List<ItemVendaResponseDTO> itemVendaList = itensVendaList
                .stream()
                .map(this::criandoItensVendaResponseDTO)
                .collect(Collectors.toList());

        return  new VendaResponseDTO(venda.getCodigo(), venda.getData(), itemVendaList);
    }

    // Metodo de conversao da ItemVenda para ItemVendaResponseDTO
    protected ItemVendaResponseDTO criandoItensVendaResponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(),
                itemVenda.getProduto().getDescricao());
    }

    // Metodo de conversao da itemVendaRequestDTO e Venda para ItemVenda
    protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaRequestDTO, Venda venda) {
        return new ItemVenda(itemVendaRequestDTO.getQuantidade(),
                itemVendaRequestDTO.getPrecoVendido(),
                new Produto(itemVendaRequestDTO.getCodigoProduto()),
                venda);
    }
}
