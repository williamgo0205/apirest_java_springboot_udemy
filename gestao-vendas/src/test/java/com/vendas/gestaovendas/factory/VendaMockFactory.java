package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.venda.model.ItemVendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class VendaMockFactory {

    public static Venda createVenda(Long codigo, LocalDate data, Cliente cliente) {
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setData(data);
        venda.setCliente(cliente);
        return venda;
    }

    public static ItemVenda createItemVenda(Long codigo, Integer quantidade, BigDecimal precoVendido,
                                            Produto produto, Venda venda) {
        ItemVenda itemVenda = new ItemVenda();
        itemVenda.setCodigo(codigo);
        itemVenda.setQuantidade(quantidade);
        itemVenda.setPrecoVendido(precoVendido);
        itemVenda.setProduto(produto);
        itemVenda.setVenda(venda);
        return itemVenda;
    }

    public static ItemVendaResponseDTO createItensVendaResponseDTO(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(),
                itemVenda.getProduto().getDescricao());
    }
}
