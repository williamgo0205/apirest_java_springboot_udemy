package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.service.CategoriaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {

    private static final Long ID_CATEGORIA_1 = 1L;
    private static final String NOME_CATEGORIA_TECNOLOGIA = "Tecnologia";

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaServiceMock;

    @Test
    public void listarTodas() {
        categoriaController.listarTodas();
        verify(categoriaServiceMock, times(1)).listarTodas();
    }

    @Test
    public void listarPorCodigoRetornoSucesso_200() {
        CategoriaResponseDTO categoriaResponseDTO =
                createCategoriaResponseDTO(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(Optional.of(createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA)))
                .when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA_1);

        ResponseEntity<CategoriaResponseDTO> response = categoriaController.listarPorCodigo(ID_CATEGORIA_1);

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getCodigo(), categoriaResponseDTO.getCodigo());
        assertEquals(response.getBody().getNome(), categoriaResponseDTO.getNome());
    }

    @Test
    public void listarPorCodigoRetornoErroNotFound_404() {
        CategoriaResponseDTO categoriaResponseDTO =
                createCategoriaResponseDTO(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        doReturn(Optional.empty())
                .when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA_1);

        ResponseEntity<CategoriaResponseDTO> response = categoriaController.listarPorCodigo(ID_CATEGORIA_1);

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(response.getBody());
    }

    private CategoriaResponseDTO createCategoriaResponseDTO(Long codigo, String nome) {
        return new CategoriaResponseDTO(codigo, nome);
    }

    private Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return  categoriaCriada;
    }
}
