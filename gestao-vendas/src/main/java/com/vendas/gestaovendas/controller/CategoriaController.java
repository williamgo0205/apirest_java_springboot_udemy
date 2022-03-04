package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.CategoriaRequestDTO;
import com.vendas.gestaovendas.dto.CategoriaResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.service.CategoriaService;
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

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET - localhost:8080/categoria
    @ApiOperation(value = "Listar Todas as Categorias Existentes",
                  nickname = "listarTodas")
    @GetMapping
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaService.listarTodas()
                .stream()
                .map(categoria -> CategoriaResponseDTO.converterParaCategoriaDTO(categoria))
                .collect(Collectors.toList());
    }

    // GET - localhost:8080/categoria/{codigo}
    @ApiOperation(value = "Listar a Categoria Informada Pelo Código",
                  nickname = "listarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> listarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Categoria> optCategoria = categoriaService.buscarPorCodigo(codigo);
        return optCategoria.isPresent()
                ? ResponseEntity.ok(CategoriaResponseDTO.converterParaCategoriaDTO(optCategoria.get()))
                : ResponseEntity.notFound().build();
    }

    // POST - localhost:8080/categoria
    @ApiOperation(value = "Salvar/Criar uma Categoria",
                  nickname = "salvarCategoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> salvar(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoriaSalva = categoriaService.salvar(categoriaRequestDTO.converterParaEntidade());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoriaResponseDTO.converterParaCategoriaDTO(categoriaSalva));
    }

    // PUT - localhost:8080/categoria/{codigo}
    @ApiOperation(value = "Atualizar uma Categoria",
                  nickname = "atualizarCategoria")
    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable(name = "codigo") Long codigo,
                                                          @Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(
                CategoriaResponseDTO.converterParaCategoriaDTO(
                        categoriaService.atualizar(codigo, categoriaRequestDTO.converterParaEntidade(codigo))));
    }

    // DELETE - localhost:8080/categoria/{codigo}
    // HttpStatus.NO_CONTENT (204) - Executado o metodo porém sem nada a retornar
    @ApiOperation(value = "Deletar uma Categoria",
                  nickname = "deletarCategoria")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long codigo){
        categoriaService.deletar(codigo);
    }
}
