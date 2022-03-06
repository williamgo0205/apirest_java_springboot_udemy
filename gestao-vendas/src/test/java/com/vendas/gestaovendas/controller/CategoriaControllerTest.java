package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.categoria.mapper.CategoriaMapper;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaRequestDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {

    private static final Long ID_CATEGORIA = 1L;
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
    public void listarPorCodigoRetornoSucesso_HttpStatus_200() {
        CategoriaResponseDTO categoriaResponseDTO = createCategoriaResponseDTO();

        doReturn(Optional.of(createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA)))
                .when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA);

        ResponseEntity<CategoriaResponseDTO> response = categoriaController.listarPorCodigo(ID_CATEGORIA);

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getCodigo(), categoriaResponseDTO.getCodigo());
        assertEquals(response.getBody().getNome(), categoriaResponseDTO.getNome());
    }

    @Test
    public void listarPorCodigoRetornoErroNotFound_HttpStatus_404() {
        doReturn(Optional.empty())
                .when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA);

        ResponseEntity<CategoriaResponseDTO> response = categoriaController.listarPorCodigo(ID_CATEGORIA);

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(response.getBody());
    }

    @Test
    public void salvarCategoriaSucesso() {
        Categoria categoria = createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        CategoriaRequestDTO categoriaRequestDTO =
                createCategoriaRequestDTO(NOME_CATEGORIA_TECNOLOGIA);

        doReturn(categoria)
                .when(categoriaServiceMock).salvar(CategoriaMapper.converterParaEntidade(categoriaRequestDTO));

        ResponseEntity<CategoriaResponseDTO> response = categoriaController.salvar(categoriaRequestDTO);

        verify(categoriaServiceMock, times(1)).salvar(any());
        assertEquals(response.getStatusCode(),       HttpStatus.CREATED);
        assertEquals(response.getBody().getCodigo(), ID_CATEGORIA);
        assertEquals(response.getBody().getNome(),   categoriaRequestDTO.getNome());
    }

    @Test
    public void atualizarCategoriaSucesso() {
        String nomeCategoriaAtualizada = "Tecnologia Atualizada";
        Categoria categoria = createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        Categoria categoriaAtualizada = createCategoria(categoria.getCodigo(), nomeCategoriaAtualizada);

        CategoriaRequestDTO categoriaRequestDTO =
                createCategoriaRequestDTO(nomeCategoriaAtualizada);

        doReturn(categoriaAtualizada)
                .when(categoriaServiceMock).atualizar(categoria.getCodigo(),
                        CategoriaMapper.converterParaEntidade(categoria.getCodigo(), categoriaRequestDTO));

        ResponseEntity<CategoriaResponseDTO> response =
                categoriaController.atualizar(categoria.getCodigo(), categoriaRequestDTO);

        verify(categoriaServiceMock, times(1)).atualizar(anyLong(), any());
        assertEquals(response.getStatusCode(),       HttpStatus.OK);
        assertEquals(response.getBody().getCodigo(), categoria.getCodigo());
        assertEquals(response.getBody().getNome(),   categoriaAtualizada.getNome());
    }

    @Test
    public void deletarCategoriaSucesso() {
        createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);

        categoriaController.deletar(ID_CATEGORIA);

        verify(categoriaServiceMock, times(1)).deletar(anyLong());
    }

    private CategoriaResponseDTO createCategoriaResponseDTO() {
        return new CategoriaResponseDTO(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
    }

    private CategoriaRequestDTO createCategoriaRequestDTO(String nome) {
        CategoriaRequestDTO categoriaRequestDTO = new CategoriaRequestDTO();
        categoriaRequestDTO.setNome(nome);
        return categoriaRequestDTO;
    }

    private Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return  categoriaCriada;
    }
}
