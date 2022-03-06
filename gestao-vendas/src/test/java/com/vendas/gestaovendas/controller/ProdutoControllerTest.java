package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.dto.produto.mapper.ProdutoMapper;
import com.vendas.gestaovendas.dto.produto.model.ProdutoRequestDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    private static final Long ID_CATEGORIA = 1L;
    private static final String NOME_CATEGORIA_TECNOLOGIA = "Tecnologia";

    private static final Long       ID_PRODUTO          = 1L;
    private static final String     DESCRICAO_PRODUTO   = "Notebook";
    private static final Integer    QUANTIDADE_PRODUTO  = 10;
    private static final BigDecimal PRECO_CUSTO_PRODUTO = new BigDecimal("2000");
    private static final BigDecimal PRECO_VENDA_PRODUTO = new BigDecimal("3000");
    private static final String     OBSERVACAO_PRODUTO  = "Notebook Dell Inspiron 15 polegadas";

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoServiceMock;

    @Test
    public void listarTodas() {
        produtoController.listarTodos(ID_CATEGORIA);
        verify(produtoServiceMock, times(1)).listarTodos(ID_CATEGORIA);
    }

    @Test
    public void listarPorCodigoECategoriaRetornoSucesso_HttpStatus_200() {
        ProdutoResponseDTO produtoResponseDTO = createProdutoResposeDTO();

        doReturn(Optional.of(createProduto()))
                .when(produtoServiceMock).buscaPorCodigoProduto(ID_PRODUTO, ID_CATEGORIA);

        ResponseEntity<ProdutoResponseDTO> response =
                produtoController.listarPorCodigoECategoria(ID_PRODUTO, ID_CATEGORIA);

        verify(produtoServiceMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        assertEquals(response.getStatusCode(),           HttpStatus.OK);
        assertEquals(response.getBody().getCodigo(),     produtoResponseDTO.getCodigo());
        assertEquals(response.getBody().getDescricao(),  produtoResponseDTO.getDescricao());
        assertEquals(response.getBody().getQuantidade(), produtoResponseDTO.getQuantidade());
        assertEquals(response.getBody().getPrecoCusto(), produtoResponseDTO.getPrecoCusto());
        assertEquals(response.getBody().getPrecoVenda(), produtoResponseDTO.getPrecoVenda());
        assertEquals(response.getBody().getObservacao(), produtoResponseDTO.getObservacao());

        assertEquals(response.getBody().getCategoria().getCodigo(), produtoResponseDTO.getCategoria().getCodigo());
        assertEquals(response.getBody().getCategoria().getNome(),   produtoResponseDTO.getCategoria().getNome());
    }

    @Test
    public void listarPorCodigoECategoriaRetornoErroNotFound_HttpStatus_404() {
        doReturn(Optional.empty())
                .when(produtoServiceMock).buscaPorCodigoProduto(ID_PRODUTO, ID_CATEGORIA);

        ResponseEntity<ProdutoResponseDTO> response =
                produtoController.listarPorCodigoECategoria(ID_PRODUTO, ID_CATEGORIA);

        verify(produtoServiceMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(response.getBody());
    }

    @Test
    public void salvarProdutoSucesso() {
        Produto produto = createProduto();
        ProdutoRequestDTO produtoRequestDTO = createProdutoRequestDTO();

        doReturn(produto)
                .when(produtoServiceMock).salvar(ID_CATEGORIA,
                        ProdutoMapper.converterParaEntidade(ID_CATEGORIA, produtoRequestDTO));

        ResponseEntity<ProdutoResponseDTO> response =
                produtoController.salvar(ID_CATEGORIA, produtoRequestDTO);

        verify(produtoServiceMock, times(1)).salvar(anyLong(), any());
        assertEquals(response.getStatusCode(),           HttpStatus.CREATED);
        assertEquals(response.getBody().getCodigo(),     produto.getCodigo());
        assertEquals(response.getBody().getDescricao(),  produtoRequestDTO.getDescricao());
        assertEquals(response.getBody().getQuantidade(), produtoRequestDTO.getQuantidade());
        assertEquals(response.getBody().getPrecoCusto(), produtoRequestDTO.getPrecoCusto());
        assertEquals(response.getBody().getPrecoVenda(), produtoRequestDTO.getPrecoVenda());
        assertEquals(response.getBody().getObservacao(), produtoRequestDTO.getObservacao());

        assertEquals(response.getBody().getCategoria().getCodigo(), produto.getCategoria().getCodigo());
        assertEquals(response.getBody().getCategoria().getNome(),   produto.getCategoria().getNome());
    }

    private CategoriaResponseDTO createCategoriaResponseDTO() {
        return new CategoriaResponseDTO(ID_CATEGORIA, NOME_CATEGORIA_TECNOLOGIA);
    }

    private Categoria createCategoria() {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(ID_CATEGORIA);
        categoriaCriada.setNome(NOME_CATEGORIA_TECNOLOGIA);
        return  categoriaCriada;
    }

    private ProdutoResponseDTO createProdutoResposeDTO() {
        return new ProdutoResponseDTO(ID_PRODUTO, DESCRICAO_PRODUTO, QUANTIDADE_PRODUTO,
                PRECO_CUSTO_PRODUTO, PRECO_VENDA_PRODUTO, OBSERVACAO_PRODUTO, createCategoriaResponseDTO());
    }

    private ProdutoRequestDTO createProdutoRequestDTO() {
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setDescricao(DESCRICAO_PRODUTO);
        produtoRequestDTO.setQuantidade(QUANTIDADE_PRODUTO);
        produtoRequestDTO.setPrecoCusto(PRECO_CUSTO_PRODUTO);
        produtoRequestDTO.setPrecoVenda(PRECO_VENDA_PRODUTO);
        produtoRequestDTO.setObservacao(OBSERVACAO_PRODUTO);

        return produtoRequestDTO;
    }

    private Produto createProduto() {
        Produto produtoCriado = new Produto();
        produtoCriado.setCodigo(ID_PRODUTO);
        produtoCriado.setDescricao(DESCRICAO_PRODUTO);
        produtoCriado.setQuantidade(QUANTIDADE_PRODUTO);
        produtoCriado.setPrecoCusto(PRECO_CUSTO_PRODUTO);
        produtoCriado.setPrecoVenda(PRECO_VENDA_PRODUTO);
        produtoCriado.setObservacao(OBSERVACAO_PRODUTO);
        produtoCriado.setCategoria(createCategoria());
        return  produtoCriado;
    }
}
