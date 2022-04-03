package com.vendas.gestaovendas.dto.categoria.mapper;

import com.vendas.gestaovendas.dto.categoria.model.CategoriaRequestDTO;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import org.mapstruct.Mapper;

@Mapper
public class CategoriaMapper {

    // Conversor responsavel pela conversao dos dados de Categoria para a CategoriaResponseDTO
    // Mapper propriamente dito
    public static CategoriaResponseDTO converterParaCategoriaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
    }

    // Conversor responsável pela conversao dos dados de CategoriaRequestDTO para a entidade Categoria
    public static Categoria converterParaEntidade(CategoriaRequestDTO categoriaRequestDTO) {
        return new Categoria(categoriaRequestDTO.getNome());
    }

    // Conversor responsável pela conversao dos dados de CategoriaRequestDTO para a entidade Categoria
    public static Categoria converterParaEntidade(Long codigo, CategoriaRequestDTO categoriaRequestDTO) {
        return new Categoria(codigo, categoriaRequestDTO.getNome());
    }
}
