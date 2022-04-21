package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaRequestDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaRequestDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public abstract class VendaMockFactory {

    // Cria uma venda
    public static Venda createVenda(Long codigo,
                                    LocalDate data,
                                    Cliente cliente) {
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setData(data);
        venda.setCliente(cliente);
        return venda;
    }

    // Cria os itens da venda
    public static ItemVenda createItemVenda(Long codigo,
                                            Integer quantidade,
                                            BigDecimal precoVendido,
                                            Produto produto,
                                            Venda venda) {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setCodigo(codigo);
        itemVenda.setQuantidade(quantidade);
        itemVenda.setPrecoVendido(precoVendido);
        itemVenda.setProduto(produto);
        itemVenda.setVenda(venda);
        return itemVenda;
    }

    // Cria o ClienteVendaResponseDTO
    public static ClienteVendaResponseDTO createClienteVendaResponseDTO(String nome,
                                                                        List<VendaResponseDTO> vendaResponseDTOList) {
        return new ClienteVendaResponseDTO(nome, vendaResponseDTOList);
    }

    //Cria a VendaResponseDTO
    public static VendaResponseDTO createVendaResponseDTO(Long codigo,
                                                          LocalDate data,
                                                          List<ItemVendaResponseDTO> itemVendaResponseDTOList) {
        return new VendaResponseDTO(codigo, data, itemVendaResponseDTOList);

    }

    //Cria o ItemVendaResponseDTO
    public static ItemVendaResponseDTO createItensVendaResponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(),
                itemVenda.getProduto().getDescricao());
    }

    //Cria a VendaRequestDTO
    public static VendaRequestDTO createVendaRequestDTO(LocalDate data,
                                                        List<ItemVendaRequestDTO> itemVendaRequestDTOList) {
        VendaRequestDTO vendaRequestDTO = new VendaRequestDTO();
        vendaRequestDTO.setData(data);
        vendaRequestDTO.setItensVendaRequestDTO(itemVendaRequestDTOList);
        return  vendaRequestDTO;
    }

    //Cria a ItemVendaRequestDTO
    public static ItemVendaRequestDTO createItemVendaRequestDTO(ItemVenda itemVenda) {
        ItemVendaRequestDTO itemVendaRequestDTO = new ItemVendaRequestDTO();
        itemVendaRequestDTO.setCodigoProduto(itemVenda.getProduto().getCodigo());
        itemVendaRequestDTO.setQuantidade(itemVenda.getQuantidade());
        itemVendaRequestDTO.setPrecoVendido(itemVenda.getPrecoVendido());
        return itemVendaRequestDTO;
    }
}
