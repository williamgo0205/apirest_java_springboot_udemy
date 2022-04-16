package com.vendas.gestaovendas.dto.venda.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@ApiModel("Venda Retorno DTO")
public class VendaResponseDTO {

    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Data")
    private LocalDate data;

    @ApiModelProperty(value = "Itens da Venda")
    private List<ItemVendaResponseDTO> itemVendaResponseDTO;

    public VendaResponseDTO(Long codigo, LocalDate data, List<ItemVendaResponseDTO> itemVendaResponseDTO) {
        this.codigo = codigo;
        this.data = data;
        this.itemVendaResponseDTO = itemVendaResponseDTO;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVendaResponseDTO> getItemVendaResponseDTO() {
        return itemVendaResponseDTO;
    }

    public void setItemVendaResponseDTO(List<ItemVendaResponseDTO> itemVendaResponseDTO) {
        this.itemVendaResponseDTO = itemVendaResponseDTO;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, data, itemVendaResponseDTO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaResponseDTO that = (VendaResponseDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(data, that.data) && Objects.equals(itemVendaResponseDTO, that.itemVendaResponseDTO);
    }
}
