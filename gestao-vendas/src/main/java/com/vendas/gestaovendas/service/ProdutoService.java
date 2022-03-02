package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.entities.Produto;
import com.vendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Busca todos os produtos da categoria informada
    public List<Produto> listarTodos(Long codigoCategoria){
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    // Busca apenas um produto repassada por codigo e código da categoria
    public Optional<Produto> buscaPorCodigoProduto(Long codigo, Long codigoCategoria){
        return produtoRepository.buscaPorCodigoProduto(codigo, codigoCategoria);
    }
}
