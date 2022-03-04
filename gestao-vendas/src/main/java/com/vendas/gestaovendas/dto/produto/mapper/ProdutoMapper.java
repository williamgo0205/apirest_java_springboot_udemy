package com.vendas.gestaovendas.dto.produto.mapper;

import com.vendas.gestaovendas.dto.categoria.mapper.CategoriaMapper;
import com.vendas.gestaovendas.dto.produto.model.ProdutoRequestDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Produto;
import org.mapstruct.Mapper;

@Mapper
public class ProdutoMapper {

    // Conversor responsavel pela conversao dos dados de Produto para a ProdutoResponseDTO
    // Mapper propriamente dito
    public static ProdutoResponseDTO converterParaProdutoDTO(Produto produto) {
        return new ProdutoResponseDTO(produto.getCodigo(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getPrecoCusto(),
                produto.getPrecoVenda(),
                produto.getObservacao(),
                CategoriaMapper.converterParaCategoriaDTO(produto.getCategoria()));
    }

    // Conversor responsavel pela conversao dos dados de ProdutoDTO para a Produto
    // Repassando o código da categoria e o ProdutoRequestDTO
    // Mapper propriamente dito
    public static Produto converterParaEntidade(Long codigoCategoria,
                                                ProdutoRequestDTO produtoRequestDTO) {
        return new Produto(produtoRequestDTO.getDescricao(),
                produtoRequestDTO.getQuantidade(),
                produtoRequestDTO.getPrecoCusto(),
                produtoRequestDTO.getPrecoVenda(),
                produtoRequestDTO.getObservacao(),
                new Categoria(codigoCategoria));
    }

    // Conversor responsavel pela conversao dos dados de ProdutoDTO para a Produto
    // Repassando o código da categoria, código do produto e o ProdutoRequestDTO
    // Mapper propriamente dito
    public static Produto converterParaEntidade(Long codigoCategoria,
                                                Long codigoProduto,
                                                ProdutoRequestDTO produtoRequestDTO) {
        return new Produto(codigoProduto,
                produtoRequestDTO.getDescricao(),
                produtoRequestDTO.getQuantidade(),
                produtoRequestDTO.getPrecoCusto(),
                produtoRequestDTO.getPrecoVenda(),
                produtoRequestDTO.getObservacao(),
                new Categoria(codigoCategoria));
    }
}
