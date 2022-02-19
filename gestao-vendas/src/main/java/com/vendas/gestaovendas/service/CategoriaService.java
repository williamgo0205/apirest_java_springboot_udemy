package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entities.Categoria;
import com.vendas.gestaovendas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Busca todas as categorias
    public List<Categoria> listarTodas(){
        return categoriaRepository.findAll();
    }

    // Busca apenas a categoria repassada por codigo
    public Optional<Categoria> buscarPorId(Long codigo){
        return categoriaRepository.findById(codigo);
    }
}
