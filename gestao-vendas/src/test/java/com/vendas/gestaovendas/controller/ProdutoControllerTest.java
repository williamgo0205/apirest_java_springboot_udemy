package com.vendas.gestaovendas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendas.gestaovendas.config.ConfigTest;
import com.vendas.gestaovendas.dto.produto.mapper.ProdutoMapper;
import com.vendas.gestaovendas.dto.produto.model.ProdutoRequestDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.factory.CategoriaMockFactory;
import com.vendas.gestaovendas.factory.ProdutoMockFactory;
import com.vendas.gestaovendas.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigTest
public class ProdutoControllerTest {

    private static final String GET_PRODUTO_LISTAR_TODAS_PATH              = "/categoria/%s/produto";
    private static final String GET_PRODUTO_BUSCAR_POR_CODIGO_PRODUTO_PATH = "/categoria/%s/produto/%s";
    private static final String POST_PRODUTO_SALVAR_PATH                   = "/categoria/%s/produto";
    private static final String PUT_PRODUTO_ATUALIZAR_PATH                 = "/categoria/%s/produto/%s";
    private static final String DELETE_PRODUTO_DELETAR_PATH                = "/categoria/%s/produto/%s";

    private static final Long       ID_CATEGORIA                  = 1L;
    private static final String     NOME_CATEGORIA_TECNOLOGIA     = "Tecnologia";

    private static final Long       ID_PRODUTO_NOTEBOOK           = 1L;
    private static final String     DESCRICAO_PRODUTO_NOTEBOOK    = "Notebook";
    private static final Integer    QUANTIDADE_PRODUTO_NOTEBOOK   = 10;
    private static final BigDecimal PRECO_CUSTO_PRODUTO_NOTEBOOK  = new BigDecimal("2000");
    private static final BigDecimal PRECO_VENDA_PRODUTO_NOTEBOOK  = new BigDecimal("3000");
    private static final String     OBSERVACAO_PRODUTO_NOTEBOOK   = "Notebook Dell Inspiron 15 polegadas";

    private static final Long       ID_PRODUTO_MONITOR            = 2L;
    private static final String     DESCRICAO_PRODUTO_MONITOR    = "Monitor Dell";
    private static final Integer    QUANTIDADE_PRODUTO_MONITOR    = 13;
    private static final BigDecimal PRECO_CUSTO_PRODUTO_MONITOR   = new BigDecimal("800");
    private static final BigDecimal PRECO_VENDA_PRODUTO_MONITOR   = new BigDecimal("1100");
    private static final String     OBSERVACAO_PRODUTO_MONITOR    = "Monitor Dell 24 polegadas";

    @MockBean
    private ProdutoService produtoServiceMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodos() throws Exception {
        // Create Categoria
        final Categoria categoriaTecnologia =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create List ProdutoResponseDTO
        final ProdutoResponseDTO produtoResponseDTONotebook =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        final ProdutoResponseDTO produtoResponseDTOMonitor =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_MONITOR, DESCRICAO_PRODUTO_MONITOR,
                        QUANTIDADE_PRODUTO_MONITOR, PRECO_CUSTO_PRODUTO_MONITOR, PRECO_VENDA_PRODUTO_MONITOR,
                        OBSERVACAO_PRODUTO_MONITOR, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        final List<ProdutoResponseDTO> produtoResponseDTOList =
                Arrays.asList(produtoResponseDTONotebook, produtoResponseDTOMonitor);

        // Create List Produto
        final Produto produtoNotebook = ProdutoMockFactory.createProduto(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        final Produto produtoMonitor  = ProdutoMockFactory.createProduto(ID_PRODUTO_MONITOR, DESCRICAO_PRODUTO_MONITOR,
                QUANTIDADE_PRODUTO_MONITOR, PRECO_CUSTO_PRODUTO_MONITOR, PRECO_VENDA_PRODUTO_MONITOR,
                OBSERVACAO_PRODUTO_MONITOR, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        List<Produto> produtoList = Arrays.asList(produtoNotebook, produtoMonitor);

        when(this.produtoServiceMock.listarTodos(ID_CATEGORIA)).thenReturn(produtoList);

        final MvcResult result = mvc.perform(get(String.format(GET_PRODUTO_LISTAR_TODAS_PATH, ID_CATEGORIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(produtoServiceMock, times(1)).listarTodos(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(createListProdutoJSON(produtoResponseDTOList)));
    }

    @Test
    public void listarPorCodigoECategoriaRetornoSucesso_HttpStatus_200() throws Exception {
        // Create Categoria
        final Categoria categoriaTecnologia =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoResponseDTO
        final ProdutoResponseDTO produtoResponseDTO =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create Produto
        final Produto produto = ProdutoMockFactory.createProduto(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);

        when(this.produtoServiceMock.buscaPorCodigoProduto(ID_PRODUTO_NOTEBOOK, ID_CATEGORIA))
                .thenReturn(Optional.of(produto));

        final MvcResult result = mvc.perform(get(String.format(GET_PRODUTO_BUSCAR_POR_CODIGO_PRODUTO_PATH,
                                                               ID_PRODUTO_NOTEBOOK, ID_CATEGORIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(produtoServiceMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        assertThat(result.getResponse().getContentAsString(), is(createProdutoJSON(produtoResponseDTO)));
    }

    @Test
    public void listarPorCodigoECategoriaRetornoErroNotFound_HttpStatus_404() throws Exception {
        // Create Categoria
        final Categoria categoriaTecnologia =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoResponseDTO
        final ProdutoResponseDTO produtoResponseDTO =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);

        when(this.produtoServiceMock.buscaPorCodigoProduto(ID_PRODUTO_NOTEBOOK, ID_CATEGORIA))
                .thenReturn(Optional.empty());

        final MvcResult result = mvc.perform(get(String.format(GET_PRODUTO_BUSCAR_POR_CODIGO_PRODUTO_PATH,
                        ID_PRODUTO_NOTEBOOK, ID_CATEGORIA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(produtoServiceMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        assertThat(result.getResponse().getContentAsString(), is(""));
    }

    @Test
    public void salvarProdutoSucesso() throws Exception {
        // Create Categoria
        final Categoria categoriaTecnologia =
                CategoriaMockFactory.createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoResponseDTO
        final ProdutoResponseDTO produtoResponseDTO =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create Produto
        final Produto produto =
                ProdutoMockFactory.createProduto(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoRequestDTO
        final ProdutoRequestDTO produtoRequestDTO =
                ProdutoMockFactory.createProdutoRequestDTO(DESCRICAO_PRODUTO_NOTEBOOK,
                QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK,
                PRECO_VENDA_PRODUTO_NOTEBOOK, OBSERVACAO_PRODUTO_NOTEBOOK);

        when(this.produtoServiceMock.salvar(ID_CATEGORIA,
                                            ProdutoMapper.converterParaEntidade(ID_CATEGORIA, produtoRequestDTO)))
                .thenReturn(produto);

        final MvcResult result = mvc.perform(post(String.format(POST_PRODUTO_SALVAR_PATH,ID_CATEGORIA))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(produtoServiceMock, times(1)).salvar(anyLong(), any());
        assertThat(result.getResponse().getContentAsString(), is(createProdutoJSON(produtoResponseDTO)));
    }

    @Test
    public void atualizarProdutoSucesso() throws Exception {
        String descricaoProdutoAtualizado = "Notebook Atualizado";

        // Create Categoria
        final Categoria categoriaTecnologia = CategoriaMockFactory.createCategoria(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoResponseDTO
        final ProdutoResponseDTO produtoResponseDTO =
                ProdutoMockFactory.createProdutoResposeDTO(ID_PRODUTO_NOTEBOOK, descricaoProdutoAtualizado,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create Produto
        final Produto produto =
                ProdutoMockFactory.createProduto(ID_PRODUTO_NOTEBOOK, DESCRICAO_PRODUTO_NOTEBOOK,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        final Produto produtoAtualizado =
                ProdutoMockFactory.createProduto(ID_PRODUTO_NOTEBOOK, descricaoProdutoAtualizado,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK, PRECO_VENDA_PRODUTO_NOTEBOOK,
                        OBSERVACAO_PRODUTO_NOTEBOOK, ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
        // Create ProdutoRequestDTO
        final ProdutoRequestDTO produtoRequestDTO =
                ProdutoMockFactory.createProdutoRequestDTO(descricaoProdutoAtualizado,
                        QUANTIDADE_PRODUTO_NOTEBOOK, PRECO_CUSTO_PRODUTO_NOTEBOOK,
                        PRECO_VENDA_PRODUTO_NOTEBOOK, OBSERVACAO_PRODUTO_NOTEBOOK);

        doReturn(produtoAtualizado)
                .when(produtoServiceMock).atualizar(ID_CATEGORIA, ID_PRODUTO_NOTEBOOK,
                        ProdutoMapper.converterParaEntidade(ID_CATEGORIA, ID_PRODUTO_NOTEBOOK, produtoRequestDTO));

        final MvcResult result = mvc.perform(put(String.format(PUT_PRODUTO_ATUALIZAR_PATH,
                                                               ID_CATEGORIA, ID_PRODUTO_NOTEBOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(produtoServiceMock, times(1)).atualizar(anyLong(), anyLong(), any());
        assertThat(result.getResponse().getContentAsString(), is(createProdutoJSON(produtoResponseDTO)));
    }

    @Test
    public void deletarProdutoSucesso() throws Exception {
        mvc.perform(delete(String.format(DELETE_PRODUTO_DELETAR_PATH, ID_CATEGORIA, ID_PRODUTO_NOTEBOOK))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(produtoServiceMock, times(1)).deletar(anyLong(), anyLong());
    }

    private String createProdutoJSON(ProdutoResponseDTO produtoResponseDTO) {
        return "{\"codigo\":".concat(valueOf(produtoResponseDTO.getCodigo()))
             + ",\"descricao\":\"".concat(valueOf(produtoResponseDTO.getDescricao())) + "\""
             + ",\"quantidade\":".concat(valueOf(produtoResponseDTO.getQuantidade()))
             + ",\"precoCusto\":".concat(valueOf(produtoResponseDTO.getPrecoCusto()))
             + ",\"precoVenda\":".concat(valueOf(produtoResponseDTO.getPrecoVenda()))
                + ",\"observacao\":\"".concat(valueOf(produtoResponseDTO.getObservacao())) + "\""
             + ",\"categoria\":{\"codigo\":".concat(valueOf(produtoResponseDTO.getCategoria().getCodigo()))
             + ",\"nome\":\"".concat(valueOf(produtoResponseDTO.getCategoria().getNome())) + "\"}}";
    }

    private String createListProdutoJSON(List<ProdutoResponseDTO> produtoResponseDTO) {
        return "[{\"codigo\":".concat(valueOf(produtoResponseDTO.get(0).getCodigo()))
              + ",\"descricao\":\"".concat(valueOf(produtoResponseDTO.get(0).getDescricao())) + "\""
              + ",\"quantidade\":".concat(valueOf(produtoResponseDTO.get(0).getQuantidade()))
              + ",\"precoCusto\":".concat(valueOf(produtoResponseDTO.get(0).getPrecoCusto()))
              + ",\"precoVenda\":".concat(valueOf(produtoResponseDTO.get(0).getPrecoVenda()))
              + ",\"observacao\":\"".concat(valueOf(produtoResponseDTO.get(0).getObservacao())) + "\""
              + ",\"categoria\":{\"codigo\":".concat(valueOf(produtoResponseDTO.get(0).getCategoria().getCodigo()))
                             + ",\"nome\":\"".concat(valueOf(produtoResponseDTO.get(0).getCategoria().getNome()))
              + "\"}}"
              + ",{\"codigo\":".concat(valueOf(produtoResponseDTO.get(1).getCodigo()))
              + ",\"descricao\":\"".concat(valueOf(produtoResponseDTO.get(1).getDescricao())) + "\""
              + ",\"quantidade\":".concat(valueOf(produtoResponseDTO.get(1).getQuantidade()))
              + ",\"precoCusto\":".concat(valueOf(produtoResponseDTO.get(1).getPrecoCusto()))
              + ",\"precoVenda\":".concat(valueOf(produtoResponseDTO.get(1).getPrecoVenda()))
              + ",\"observacao\":\"".concat(valueOf(produtoResponseDTO.get(1).getObservacao())) + "\""
              + ",\"categoria\":{\"codigo\":".concat(valueOf(produtoResponseDTO.get(1).getCategoria().getCodigo()))
                             + ",\"nome\":\"".concat(valueOf(produtoResponseDTO.get(1).getCategoria().getNome()))
              + "\"}}]";
    }
}
