package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaRequestDTO;
import com.vendas.gestaovendas.service.VendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    // GET - localhost:8080/venda/cliente/{codigoCliente}
    @ApiOperation(value = "Listar vendas pelo codigo do cliente",
            nickname = "listarVendasPorCodigoCliente")
    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendasPorCliente(@PathVariable(name = "codigoCliente")
                                                                                        Long codigoCliente) {
        return ResponseEntity.ok(vendaService.listarVendasPorCliente(codigoCliente));
    }

    // GET - localhost:8080/venda/{codigoVenda}
    @ApiOperation(value = "Listar venda pelo codigo da Venda",
            nickname = "listarVendaPorCodigo")
    @GetMapping("/{codigoVenda}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCodigo(@PathVariable(name = "codigoVenda")
                                                                                      Long codigoVenda) {
        return ResponseEntity.ok(vendaService.listarVendaPorCodigo(codigoVenda));
    }

    // POST - localhost:8080/venda/cliente/{codigoCliente}
    @ApiOperation(value = "Registar Venda",
            nickname = "registarVenda")
    @PostMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> salvar(@PathVariable(name = "codigoCliente") Long codigoCliente,
                                                          @RequestBody VendaRequestDTO vendaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vendaService.salvar(codigoCliente,vendaRequestDTO));
    }
}
