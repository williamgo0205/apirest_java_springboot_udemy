package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entities.Produto;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    // Busca todos os produtos da categoria informada
    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    // Busca apenas um produto repassada por codigo e código da categoria
    public Optional<Produto> buscaPorCodigoProduto(Long codigo, Long codigoCategoria) {
        return produtoRepository.buscaPorCodigoProduto(codigo, codigoCategoria);
    }

    // Metodo para salvar um produto no banco de dados
    public Produto salvar(Produto produto) {
        validarCategoriaExistenteProduto(produto.getCategoria().getCodigo());
        validarProdutoDuplicado(produto);
        return produtoRepository.save(produto);
    }

    // Método para validações referentes a Categoria informada
    private void validarCategoriaExistenteProduto(Long codigoCategoria) {
        if (codigoCategoria == null) {
            throw new RegraNegocioException("A Categoria não pode ser nula.");
        }
        if (categoriaService.buscarPorCodigo(codigoCategoria).isEmpty()) {
            throw new RegraNegocioException(
                    String.format("A Categoria de código: %s informada não existe no Banco de Dados.",
                            codigoCategoria));
        }
    }

    // Método para validações referentes a produto duplicado
    private void validarProdutoDuplicado(Produto produto) {
        if (produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(),
                produto.getDescricao()).isPresent()) {
            throw new RegraNegocioException(
                    String.format("O produto: %s já está cadastrado no sistema.",
                            produto.getDescricao()));
        }
    }
}
