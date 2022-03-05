package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.repository.CategoriaRepository;
import com.vendas.gestaovendas.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
    private CategoriaRepository categoriaRepositoryMock;

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

        verify(produtoRepositoryMock, times(1)).findByCategoriaCodigo(any());

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
