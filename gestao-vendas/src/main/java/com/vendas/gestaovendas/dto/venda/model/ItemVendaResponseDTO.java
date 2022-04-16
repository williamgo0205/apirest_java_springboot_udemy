package com.vendas.gestaovendas.dto.venda.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("Itens Venda Retorno DTO")
public class ItemVendaResponseDTO {

    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

    @ApiModelProperty(value = "Preço Vendido")
    private BigDecimal precoVendido;

    @ApiModelProperty(value = "Código Produto")
    private Long codProduto;

    @ApiModelProperty(value = "Descrição Produto")
    private String produtoDescricao;

    public ItemVendaResponseDTO(Long codigo, Integer quantidade, BigDecimal precoVendido, Long codProduto,
                                String produtoDescricao) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.precoVendido = precoVendido;
        this.codProduto = codProduto;
        this.produtoDescricao = produtoDescricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVendido() {
        return precoVendido;
    }

    public void setPrecoVendido(BigDecimal precoVendido) {
        this.precoVendido = precoVendido;
    }

    public Long getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(Long codProduto) {
        this.codProduto = codProduto;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }
}
