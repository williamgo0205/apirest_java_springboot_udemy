package com.vendas.gestaovendas.factory;

import com.vendas.gestaovendas.dto.categoria.model.CategoriaRequestDTO;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;

public abstract class CategoriaMockFactory {

    public static CategoriaResponseDTO createCategoriaResponseDTO(Long codigo, String nome) {
        return new CategoriaResponseDTO(codigo, nome);
    }

    public static CategoriaRequestDTO createCategoriaRequestDTO(String nome) {
        CategoriaRequestDTO categoriaRequestDTO = new CategoriaRequestDTO();
        categoriaRequestDTO.setNome(nome);
        return categoriaRequestDTO;
    }

    public static Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return categoriaCriada;
    }
}
