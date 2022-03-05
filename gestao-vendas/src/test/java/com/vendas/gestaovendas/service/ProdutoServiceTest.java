package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    private static final Long       ID_CATEGORIA_1            = 1L;
    private static final String     NOME_CATEGORIA_TECNOLOGIA = "Tecnologia";

    private static final Long       ID_PRODUTO_1          = 1L;
    private static final String     DESCRICAO_PRODUTO_1   = "Notebook";
    private static final Integer    QUANTIDADE_PRODUTO_1  = 10;
    private static final BigDecimal PRECO_CUSTO_PRODUTO_1 = new BigDecimal("2000");
    private static final BigDecimal PRECO_VENDA_PRODUTO_1 = new BigDecimal("3000");
    private static final String     OBSERVACAO_PRODUTO_1  = "Notebook Dell Inspiron 15 polegadas";

    private static final Long       ID_PRODUTO_2          = 2L;
    private static final String     DESCRICAO_PRODUTO_2   = "Monitor Dell";
    private static final Integer    QUANTIDADE_PRODUTO_2  = 13;
    private static final BigDecimal PRECO_CUSTO_PRODUTO_2 = new BigDecimal("800");
    private static final BigDecimal PRECO_VENDA_PRODUTO_2 = new BigDecimal("1100");
    private static final String     OBSERVACAO_PRODUTO_2  = "Monitor Dell 24 polegadas";

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepositoryMock;

    @Mock
    private CategoriaService categoriaServiceMock;

    @Test
    public void listarTodosProdutosTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto1 = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);
        Produto produto2 = createProduto(ID_PRODUTO_2, DESCRICAO_PRODUTO_2, QUANTIDADE_PRODUTO_2, PRECO_CUSTO_PRODUTO_2,
                PRECO_VENDA_PRODUTO_2, OBSERVACAO_PRODUTO_2, categoria);

        List<Produto> produtoList = new ArrayList<>();
        produtoList.add(produto1);
        produtoList.add(produto2);

        doReturn(produtoList).when(produtoRepositoryMock).findByCategoriaCodigo(ID_CATEGORIA_1);

        List<Produto> ProdutoListService = produtoService.listarTodos(ID_CATEGORIA_1);

        verify(produtoRepositoryMock, times(1)).findByCategoriaCodigo(anyLong());

        assertEquals(ProdutoListService.get(0).getCodigo(),     produto1.getCodigo());
        assertEquals(ProdutoListService.get(0).getDescricao(),  produto1.getDescricao());
        assertEquals(ProdutoListService.get(0).getQuantidade(), produto1.getQuantidade());
        assertEquals(ProdutoListService.get(0).getPrecoCusto(), produto1.getPrecoCusto());
        assertEquals(ProdutoListService.get(0).getPrecoVenda(), produto1.getPrecoVenda());
        assertEquals(ProdutoListService.get(0).getObservacao(), produto1.getObservacao());
        assertEquals(ProdutoListService.get(0).getCategoria().getCodigo(), produto1.getCategoria().getCodigo());
        assertEquals(ProdutoListService.get(0).getCategoria().getNome(),   produto1.getCategoria().getNome());

        assertEquals(ProdutoListService.get(1).getCodigo(),     produto2.getCodigo());
        assertEquals(ProdutoListService.get(1).getDescricao(),  produto2.getDescricao());
        assertEquals(ProdutoListService.get(1).getQuantidade(), produto2.getQuantidade());
        assertEquals(ProdutoListService.get(1).getPrecoCusto(), produto2.getPrecoCusto());
        assertEquals(ProdutoListService.get(1).getPrecoVenda(), produto2.getPrecoVenda());
        assertEquals(ProdutoListService.get(1).getObservacao(), produto2.getObservacao());
        assertEquals(ProdutoListService.get(1).getCategoria().getCodigo(), produto2.getCategoria().getCodigo());
        assertEquals(ProdutoListService.get(1).getCategoria().getNome(),   produto2.getCategoria().getNome());
    }

    @Test
    public void buscaPorCodigoProdutoTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.of(produto)).when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, ID_CATEGORIA_1);

        Optional<Produto> optProduto = produtoService.buscaPorCodigoProduto(ID_PRODUTO_1, ID_CATEGORIA_1);

        verify(produtoRepositoryMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());

        assertEquals(optProduto.get().getCodigo(),     produto.getCodigo());
        assertEquals(optProduto.get().getDescricao(), produto.getDescricao());
        assertEquals(optProduto.get().getQuantidade(), produto.getQuantidade());
        assertEquals(optProduto.get().getPrecoCusto(), produto.getPrecoCusto());
        assertEquals(optProduto.get().getPrecoVenda(), produto.getPrecoVenda());
        assertEquals(optProduto.get().getObservacao(), produto.getObservacao());
        assertEquals(optProduto.get().getCategoria().getCodigo(), produto.getCategoria().getCodigo());
        assertEquals(optProduto.get().getCategoria().getNome(),   produto.getCategoria().getNome());
    }

    @Test
    public void salvarProdutoTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.of(categoria)).when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA_1);
        doReturn(Optional.of(produto)).when(produtoRepositoryMock)
                .findByCategoriaCodigoAndDescricao(ID_CATEGORIA_1, DESCRICAO_PRODUTO_1);
        doReturn(produto).when(produtoRepositoryMock).save(any());

        Produto produtoSalvo = produtoService.salvar(ID_CATEGORIA_1, produto);

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(any());
        verify(produtoRepositoryMock, times(1))
                .findByCategoriaCodigoAndDescricao(anyLong(), anyString());
        verify(produtoRepositoryMock, times(1)).save(any());

        assertEquals(produto.getCodigo(),     produtoSalvo.getCodigo());
        assertEquals(produto.getDescricao(),  produtoSalvo.getDescricao());
        assertEquals(produto.getQuantidade(), produtoSalvo.getQuantidade());
        assertEquals(produto.getPrecoCusto(), produtoSalvo.getPrecoCusto());
        assertEquals(produto.getPrecoVenda(), produtoSalvo.getPrecoVenda());
        assertEquals(produto.getObservacao(), produtoSalvo.getObservacao());
        assertEquals(produto.getCategoria().getCodigo(), produtoSalvo.getCategoria().getCodigo());
        assertEquals(produto.getCategoria().getNome(),   produtoSalvo.getCategoria().getNome());
    }

    @Test
    public void erroSalvarProdutoCategoriaNulaTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        assertThrows(RegraNegocioException.class, () -> produtoService.salvar(null, produto));

        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void erroSalvarProdutoCategoriaInexistenteTest() {
        Long codigoCategoriaInexistente = 3L;
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        assertThrows(RegraNegocioException.class, () -> produtoService.salvar(codigoCategoriaInexistente, produto));

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(any());
        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void erroSalvarProdutoDuplicadoTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produtoExistente = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1,
                PRECO_CUSTO_PRODUTO_1, PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);
        Produto produtoNovo = createProduto(ID_PRODUTO_2, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1,
                PRECO_CUSTO_PRODUTO_1, PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.of(categoria)).when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA_1);
        doReturn(Optional.of(produtoExistente)).when(produtoRepositoryMock)
                .findByCategoriaCodigoAndDescricao(ID_CATEGORIA_1, DESCRICAO_PRODUTO_1);

        assertThrows(RegraNegocioException.class, () -> produtoService.salvar(ID_CATEGORIA_1, produtoNovo));

        verify(categoriaServiceMock, times(1)).buscarPorCodigo(any());
        verify(produtoRepositoryMock, times(1))
                .findByCategoriaCodigoAndDescricao(anyLong(), anyString());
        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void atualizarProdutoTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.of(produto)).when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, ID_CATEGORIA_1);
        doReturn(Optional.of(categoria)).when(categoriaServiceMock).buscarPorCodigo(ID_CATEGORIA_1);
        doReturn(Optional.of(produto)).when(produtoRepositoryMock)
                .findByCategoriaCodigoAndDescricao(ID_CATEGORIA_1, DESCRICAO_PRODUTO_1);
        doReturn(produto).when(produtoRepositoryMock).save(any());

        Produto produtoSalvo = produtoService.atualizar(ID_CATEGORIA_1, ID_PRODUTO_1, produto);

        verify(produtoRepositoryMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        verify(produtoRepositoryMock, times(1))
                .findByCategoriaCodigoAndDescricao(anyLong(), anyString());
        verify(produtoRepositoryMock, times(1)).save(any());

        assertEquals(produto.getCodigo(),     produtoSalvo.getCodigo());
        assertEquals(produto.getDescricao(),  produtoSalvo.getDescricao());
        assertEquals(produto.getQuantidade(), produtoSalvo.getQuantidade());
        assertEquals(produto.getPrecoCusto(), produtoSalvo.getPrecoCusto());
        assertEquals(produto.getPrecoVenda(), produtoSalvo.getPrecoVenda());
        assertEquals(produto.getObservacao(), produtoSalvo.getObservacao());
        assertEquals(produto.getCategoria().getCodigo(), produtoSalvo.getCategoria().getCodigo());
        assertEquals(produto.getCategoria().getNome(),   produtoSalvo.getCategoria().getNome());
    }

    @Test
    public void erroAtualizarProdutoInexistenteTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.empty())
                .when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, ID_CATEGORIA_1);

        assertThrows(EmptyResultDataAccessException.class, () ->
                produtoService.atualizar(ID_CATEGORIA_1, ID_PRODUTO_1, produto));

        verify(produtoRepositoryMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void erroAlterarProdutoCategoriaNulaTest() {
        createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, new Categoria());

        doReturn(Optional.of(produto))
                .when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, null);

        assertThrows(RegraNegocioException.class, () ->
                produtoService.atualizar(null, ID_PRODUTO_1, produto));

        verify(produtoRepositoryMock, times(1)).buscaPorCodigoProduto(anyLong(), any());
        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void erroAlterarProdutoCategoriaInexistenteTest() {
        Long codigoCategoriaInexistente = 3L;
        createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, new Categoria(codigoCategoriaInexistente));

        doReturn(Optional.of(produto))
                .when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, codigoCategoriaInexistente);
        doReturn(Optional.empty())
                .when(categoriaServiceMock).buscarPorCodigo(codigoCategoriaInexistente);

        assertThrows(RegraNegocioException.class, () ->
                produtoService.atualizar(codigoCategoriaInexistente, ID_PRODUTO_1, produto));

        verify(produtoRepositoryMock, times(1)).buscaPorCodigoProduto(anyLong(), anyLong());
        verify(categoriaServiceMock, times(1)).buscarPorCodigo(anyLong());
        verify(produtoRepositoryMock, never()).save(any());
    }

    @Test
    public void deletarProdutoTest() {
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        Produto produto = createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.of(produto))
                .when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, ID_CATEGORIA_1);

        produtoService.deletar(ID_CATEGORIA_1, ID_PRODUTO_1);

        verify(produtoRepositoryMock, times(1)).delete(any());
    }

    @Test
    public void erroDeletarProdutoInexistenteTest() {
        Long codigoCategoriaInexistente = 3L;
        Categoria categoria = createCategoria(ID_CATEGORIA_1, NOME_CATEGORIA_TECNOLOGIA);

        createProduto(ID_PRODUTO_1, DESCRICAO_PRODUTO_1, QUANTIDADE_PRODUTO_1, PRECO_CUSTO_PRODUTO_1,
                PRECO_VENDA_PRODUTO_1, OBSERVACAO_PRODUTO_1, categoria);

        doReturn(Optional.empty())
                .when(produtoRepositoryMock).buscaPorCodigoProduto(ID_PRODUTO_1, codigoCategoriaInexistente);

        assertThrows(EmptyResultDataAccessException.class, () ->
                produtoService.deletar(codigoCategoriaInexistente, ID_PRODUTO_1));

        verify(produtoRepositoryMock, never()).delete(any());
    }


    private Categoria createCategoria(Long codCategoria, String nome) {
        Categoria categoriaCriada = new Categoria();
        categoriaCriada.setCodigo(codCategoria);
        categoriaCriada.setNome(nome);
        return  categoriaCriada;
    }

    private Produto createProduto(Long codProduto, String descricao, Integer quantidade, BigDecimal precoCusto,
                                  BigDecimal precoVenda, String observacao, Categoria categoria) {
        Produto produtoCriado = new Produto();
        produtoCriado.setCodigo(codProduto);
        produtoCriado.setDescricao(descricao);
        produtoCriado.setQuantidade(quantidade);
        produtoCriado.setPrecoCusto(precoCusto);
        produtoCriado.setPrecoVenda(precoVenda);
        produtoCriado.setObservacao(observacao);
        produtoCriado.setCategoria(categoria);
        return  produtoCriado;
    }
}
