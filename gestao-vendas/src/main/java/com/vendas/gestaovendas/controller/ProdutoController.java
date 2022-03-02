package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.entities.Produto;
import com.vendas.gestaovendas.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(tags = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // GET - localhost:8080/produto
    @ApiOperation(value = "Listar Todos os Produtos Existentes")
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    // GET - localhost:8080/produto/{codigo}
    @ApiOperation(value = "Listar o Produto Informado Pelo Código")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> listarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Produto> optionalProduto = produtoService.buscarPorCodigo(codigo);
        return optionalProduto.isPresent()
                ? ResponseEntity.ok(optionalProduto)
                : ResponseEntity.notFound().build();
    }
}
