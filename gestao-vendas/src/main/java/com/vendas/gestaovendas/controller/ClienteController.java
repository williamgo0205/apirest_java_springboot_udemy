package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.categoria.mapper.CategoriaMapper;
import com.vendas.gestaovendas.dto.cliente.mapper.ClienteMapper;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.service.ClienteService;
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
import java.util.stream.Collectors;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET - localhost:8080/cliente
    @ApiOperation(value = "Listar Todos os Clientes Existentes",
            nickname = "listarTodosClientes")
    @GetMapping
    public List<ClienteResponseDTO> listarTodos() {
        return clienteService.listarTodos()
                .stream()
                .map(cliente -> ClienteMapper.converterParaClienteDTO(cliente))
                .collect(Collectors.toList());
    }

    // GET - localhost:8080/cliente/{codigo}
    @ApiOperation(value = "Listar o Cliente Informado Pelo Código",
            nickname = "listarPorCodigoDeCliente")
    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> listarPorCodigo(@PathVariable(name = "codigo") Long codigo) {
        Optional<Cliente> optCliente = clienteService.buscarPorCodigo(codigo);
        return optCliente.isPresent()
                ? ResponseEntity.ok(ClienteMapper.converterParaClienteDTO(optCliente.get()))
                : ResponseEntity.notFound().build();
    }

}