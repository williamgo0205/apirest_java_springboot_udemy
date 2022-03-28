package com.vendas.gestaovendas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendas.gestaovendas.config.ConfigTest;
import com.vendas.gestaovendas.dto.categoria.mapper.CategoriaMapper;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaRequestDTO;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.factory.CategoriaMockFactory;
import com.vendas.gestaovendas.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigTest
public class CategoriaControllerTest {

    private static final String GET_CATEGORIA_LISTAR_TODAS_PATH      = "/categoria";
    private static final String GET_CATEGORIA_LISTAR_POR_CODIGO_PATH = "/categoria/%s";
    private static final String POST_CATEGORIA_SALVAR_PATH           = "/categoria";
    private static final String PUT_CATEGORIA_ATUALIZAR_PATH         = "/categoria/%s";
    private static final String DELETE_CATEGORIA_DELETAR_PATH        = "/categoria/%s";

    private static final Long   ID_CATEGORIA_TECNOLOGIA              = 1L;
    private static final String NOME_CATEGORIA_TECNOLOGIA            = "Tecnologia";
    private static final Long   ID_CATEGORIA_AUTOMOTIVA              = 2L;
    private static final String NOME_CATEGORIA_TECNOLOGIA_AUTOMOTIVA = "Automotiva";

    @MockBean
    private CategoriaService categoriaServiceMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodas() throws Exception {
        // Create List CategoriaResponseDTO
        CategoriaResponseDTO categoriaResponseDTOTecnologia =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        CategoriaResponseDTO categoriaResponseDTOAutomotiva =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_AUTOMOTIVA, NOME_CATEGORIA_TECNOLOGIA_AUTOMOTIVA);
        final List<CategoriaResponseDTO> categoriaResponseDTOList =
                Arrays.asList(categoriaResponseDTOTecnologia, categoriaResponseDTOAutomotiva);

        // Create List Categoria
        final Categoria categoriaTecnologia =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        final Categoria categoriaAutomotiva =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA_AUTOMOTIVA, NOME_CATEGORIA_TECNOLOGIA_AUTOMOTIVA);
        List<Categoria> categoriaList = Arrays.asList(categoriaTecnologia, categoriaAutomotiva);

        when(this.categoriaServiceMock.listarTodas()).thenReturn(categoriaList);

        final MvcResult result = mvc.perform(get(String.format(GET_CATEGORIA_LISTAR_TODAS_PATH))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(categoriaServiceMock, times(1)).listarTodas();
        assertThat(result.getResponse().getContentAsString(), is(createListCategoriaJSON(categoriaResponseDTOList)));
    }

    @Test
    public void listarPorCodigoRetornoSucesso_HttpStatus_200() throws Exception {
        // Create CategoriaResponseDTO
        final CategoriaResponseDTO categoriaResponseDTO =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create List Categoria
        final Categoria categoria =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);

        when(categoriaServiceMock.buscarPorCodigo(ID_CATEGORIA_TECNOLOGIA)).thenReturn(Optional.of(categoria));

        final MvcResult result = mvc.perform(get(String.format(GET_CATEGORIA_LISTAR_POR_CODIGO_PATH, ID_CATEGORIA_TECNOLOGIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(createCategoriaJSON(categoriaResponseDTO)));
    }

    @Test
    public void listarPorCodigoRetornoErroNotFound_HttpStatus_404() throws Exception {
        // Create CategoriaResponseDTO
        final CategoriaResponseDTO categoriaResponseDTO =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create List Categoria
        CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);

        when(categoriaServiceMock.buscarPorCodigo(ID_CATEGORIA_TECNOLOGIA)).thenReturn(Optional.empty());

        final MvcResult result = mvc.perform(get(String.format(GET_CATEGORIA_LISTAR_POR_CODIGO_PATH, ID_CATEGORIA_TECNOLOGIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(""));
    }

    @Test
    public void salvarCategoriaSucesso() throws Exception {
        // Create CategoriaResponseDTO
        final CategoriaResponseDTO categoriaResponseDTO =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create Categoria
        final Categoria categoria =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create CategoriaRequestDTO
        final CategoriaRequestDTO categoriaRequestDTO =
                CategoriaMockFactory.createCategoriaRequestDTO(NOME_CATEGORIA_TECNOLOGIA);

        when(categoriaServiceMock.salvar(CategoriaMapper.converterParaEntidade(categoriaRequestDTO)))
                .thenReturn(categoria);

        final MvcResult result = mvc.perform(post(String.format(POST_CATEGORIA_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(categoriaServiceMock, times(1)).salvar(any());
        assertThat(result.getResponse().getContentAsString(), is(createCategoriaJSON(categoriaResponseDTO)));
    }

    @Test
    public void atualizarCategoriaSucesso() throws Exception {
        String nomeCategoriaAtualizada = "Tecnologia Atualizada";

        // Create CategoriaResponseDTO
        final CategoriaResponseDTO categoriaResponseDTO =
                CategoriaMockFactory.createCategoriaResponseDTO(ID_CATEGORIA_TECNOLOGIA, nomeCategoriaAtualizada);
        // Create Categoria e CategoriaAtualizada
        final Categoria categoria =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);
        final Categoria categoriaAtualizada =
                CategoriaMockFactory.createCategoria(categoria.getCodigo(), nomeCategoriaAtualizada);
        // Create CategoriaRequestDTO
        final CategoriaRequestDTO categoriaRequestDTO =
                CategoriaMockFactory.createCategoriaRequestDTO(nomeCategoriaAtualizada);

        doReturn(categoriaAtualizada)
                .when(categoriaServiceMock).atualizar(categoria.getCodigo(),
                        CategoriaMapper.converterParaEntidade(categoria.getCodigo(), categoriaRequestDTO));

        final MvcResult result = mvc.perform(put(String.format(PUT_CATEGORIA_ATUALIZAR_PATH, categoria.getCodigo()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(categoriaServiceMock, times(1)).atualizar(anyLong(), any());
        assertThat(result.getResponse().getContentAsString(), is(createCategoriaJSON(categoriaResponseDTO)));
    }

    @Test
    public void deletarCategoriaSucesso() throws Exception {
        CategoriaMockFactory.createCategoria(ID_CATEGORIA_TECNOLOGIA, NOME_CATEGORIA_TECNOLOGIA);

        mvc.perform(delete(String.format(DELETE_CATEGORIA_DELETAR_PATH, ID_CATEGORIA_TECNOLOGIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(categoriaServiceMock, times(1)).deletar(anyLong());
    }

    private String createCategoriaJSON(CategoriaResponseDTO categoriaResponseDTO) {
        return "{\"codigo\":".concat(valueOf(categoriaResponseDTO.getCodigo()))
                + ",\"nome\":\"".concat(valueOf(categoriaResponseDTO.getNome())) + "\"}";
    }

    private String createListCategoriaJSON(List<CategoriaResponseDTO> categoriaResponseDTOList) {
        return "[{\"codigo\":".concat(valueOf(categoriaResponseDTOList.get(0).getCodigo()))
             + ",\"nome\":\"".concat(valueOf(categoriaResponseDTOList.get(0).getNome())) + "\"}"
                + ",{\"codigo\":".concat(valueOf(categoriaResponseDTOList.get(1).getCodigo()))
             + ",\"nome\":\"".concat(valueOf(categoriaResponseDTOList.get(1).getNome())) + "\"}]";
    }
}
