package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.produto.model.ProdutoRequestDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Produto;

import java.math.BigDecimal;

public abstract class ProdutoMockFactory {

    public static ProdutoResponseDTO createProdutoResposeDTO(Long codigo, String descricao, Integer quantidade,
                                                       BigDecimal precoCusto, BigDecimal precoVenda,
                                                       String observacao, Long codigoCategoria, String nomeCategoria) {
        return new ProdutoResponseDTO(codigo, descricao, quantidade, precoCusto, precoVenda, observacao,
                CategoriaMockFactory.createCategoriaResponseDTO(codigoCategoria, nomeCategoria));
    }

    public static ProdutoRequestDTO createProdutoRequestDTO(String descricao, Integer quantidade,
                                                      BigDecimal precoCusto, BigDecimal precoVenda,
                                                      String observacao) {
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setDescricao(descricao);
        produtoRequestDTO.setQuantidade(quantidade);
        produtoRequestDTO.setPrecoCusto(precoCusto);
        produtoRequestDTO.setPrecoVenda(precoVenda);
        produtoRequestDTO.setObservacao(observacao);

        return produtoRequestDTO;
    }

    public static Produto createProduto(Long codigo, String descricao, Integer quantidade,
                                  BigDecimal precoCusto, BigDecimal precoVenda,
                                  String observacao, Long codCategoria, String nomeCategoria) {
        Produto produtoCriado = new Produto();
        produtoCriado.setCodigo(codigo);
        produtoCriado.setDescricao(descricao);
        produtoCriado.setQuantidade(quantidade);
        produtoCriado.setPrecoCusto(precoCusto);
        produtoCriado.setPrecoVenda(precoVenda);
        produtoCriado.setObservacao(observacao);
        produtoCriado.setCategoria(CategoriaMockFactory.createCategoria(codCategoria, nomeCategoria));
        return  produtoCriado;
    }
}
