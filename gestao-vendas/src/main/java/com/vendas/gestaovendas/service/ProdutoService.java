package com.vendas.gestaovendas.service;

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

    // Busca todos os produtos
    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    // Busca apenas um produto repassada por codigo
    public Optional<Produto> buscarPorCodigo(Long codigo){
        return produtoRepository.findById(codigo);
    }
}
