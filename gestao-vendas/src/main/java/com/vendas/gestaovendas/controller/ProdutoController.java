package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.produto.mapper.ProdutoMapper;
import com.vendas.gestaovendas.dto.produto.model.ProdutoRequestDTO;
import com.vendas.gestaovendas.dto.produto.model.ProdutoResponseDTO;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // GET - localhost:8080/categoria/{codigoCategoria}/produto
    @ApiOperation(value = "Listar Todos os Produtos Existentes de uma Determinada Categoria",
            nickname = "listarTodos")
    @GetMapping
    public List<ProdutoResponseDTO> listarTodos(@PathVariable(name = "codigoCategoria") Long codigoCategoria) {
        return produtoService.listarTodos(codigoCategoria)
                .stream()
                .map(produto -> ProdutoMapper.converterParaProdutoDTO(produto))
                .collect(Collectors.toList());
    }

    // GET - localhost:8080/categoria/{codigoCategoria}/produto/{codigo}
    @ApiOperation(value = "Listar o Produto Informado Pelo Codigo e Categoria Informados",
            nickname = "listarPorCodigoECategoria")
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> listarPorCodigoECategoria(
            @PathVariable(name = "codigoCategoria") Long codigoCategoria,
            @PathVariable(name = "codigo") Long codigo) {
        Optional<Produto> optProduto = produtoService.buscaPorCodigoProduto(codigo, codigoCategoria);
        return optProduto.isPresent()
                ? ResponseEntity.ok(ProdutoMapper.converterParaProdutoDTO(optProduto.get()))
                : ResponseEntity.notFound().build();
    }

    // POST - localhost:8080/categoria/{codigoCategoria}/produto
    @ApiOperation(value = "Salvar/Criar um Produto",
            nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@PathVariable(name = "codigoCategoria") Long codigoCategoria,
                                                     @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produtoSalvar = produtoService.salvar(codigoCategoria,
                ProdutoMapper.converterParaEntidade(codigoCategoria, produtoRequestDTO));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoMapper.converterParaProdutoDTO(produtoSalvar));
    }

    // PUT - localhost:8080/categoria/{codigoCategoria}/produto/{codigoProduto}
    @ApiOperation(value = "Atualizar um Produto",
            nickname = "atualizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable(name = "codigoCategoria") Long codigoCategoria,
                                                        @PathVariable(name = "codigoProduto") Long codigoProduto,
                                                        @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Produto produtoAtualizado = produtoService.atualizar(codigoCategoria, codigoProduto,
                                            ProdutoMapper.converterParaEntidade(codigoCategoria,
                                                                                codigoProduto,
                                                                                produtoRequestDTO));

        return ResponseEntity.ok(ProdutoMapper.converterParaProdutoDTO(produtoAtualizado));
    }

    // DELETE - localhost:8080/categoria/{codigoCategoria}/produto/{codigoProduto}
    // HttpStatus.NO_CONTENT (204) - Executado o metodo porém sem nada a retornar
    @ApiOperation(value = "Deletar um Produto",
            nickname = "deletarProduto")
    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable(name = "codigoCategoria") Long codigoCategoria,
                        @PathVariable(name = "codigoProduto") Long codigoProduto) {
        produtoService.deletar(codigoCategoria, codigoProduto);
    }
}
