package com.vendas.gestaovendas.service;

import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.exception.RegraNegocioException;
import com.vendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public Produto salvar(Long codigoCategoria, Produto produto) {
        validarSeCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto);
        return produtoRepository.save(produto);
    }

    // Metodo para Atualizar um produto no banco de dados
    public Produto atualizar(Long codigoCategoria, Long codigoProduto, Produto produto) {
        Produto produtoAtualizar = validarSeProdutoExiste(codigoProduto, codigoCategoria);
        validarSeCategoriaDoProdutoExiste(produto.getCategoria().getCodigo());
        validarProdutoDuplicado(produto);

         /* BeanUtils substitui a entidade recebida via parametro no banco de dados
          > SOURCE = entidade a ser salva (recebida por parametro)
          > TARGET = entidade do banco de dados
          > Terceiro parâmetro = campo que não deve ser modificado nessa acao*/
        BeanUtils.copyProperties(produto, produtoAtualizar, "codigo");

        // Persiste a entidade no banco de dados
        return produtoRepository.save(produtoAtualizar);
    }

    // Método para deletar um Produto no banco de dados
    public void deletar(Long codigoCategoria, Long codigoProduto){
        Produto produto = validarSeProdutoExiste(codigoProduto, codigoCategoria);
        produtoRepository.delete(produto);
    }

    // Método para validações referentes a Categoria informada
    private void validarSeCategoriaDoProdutoExiste(Long codigoCategoria) {
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
        Optional<Produto> optProdutoEncontrado = produtoRepository
                .findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());

        if (optProdutoEncontrado.isPresent() && optProdutoEncontrado.get().getCodigo() != produto.getCodigo()) {
            throw new RegraNegocioException(
                    String.format("O produto: %s já está cadastrado no sistema.", produto.getDescricao()));
        }
    }

    // Método para validar se um produto existe
    private Produto validarSeProdutoExiste(Long codigoProduto, Long codigoCategoria) {
        Optional<Produto> optProduto = buscaPorCodigoProduto(codigoProduto, codigoCategoria);
        if (optProduto.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return optProduto.get();
    }
}
