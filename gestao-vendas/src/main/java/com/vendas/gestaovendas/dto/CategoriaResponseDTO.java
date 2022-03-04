package com.vendas.gestaovendas.dto;

import com.vendas.gestaovendas.entity.Categoria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Categoria Retorno DTO")
public class CategoriaResponseDTO {
    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Nome")
    private String nome;

    public Long getCodigo() {
        return codigo;
    }

    // Construtor CategoriaResponseDTO
    public CategoriaResponseDTO(Long codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    // Conversor responsavel pela conversao dos dados de Categoria para a CategoriaResponseDTO
    // Mapper propriamente dito
    public static CategoriaResponseDTO converterParaCategoriaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
