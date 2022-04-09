package com.vendas.gestaovendas.controller;

import com.vendas.gestaovendas.dto.cliente.mapper.ClienteMapper;
import com.vendas.gestaovendas.dto.cliente.model.ClienteRequestDTO;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.service.ClienteService;
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

    // POST - localhost:8080/cliente
    @ApiOperation(value = "Salvar/Criar um Cliente",
            nickname = "salvarCliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteSalvo = clienteService.salvar(ClienteMapper.converterParaEntidade(clienteRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.converterParaClienteDTO(clienteSalvo));
    }

    // PUT - localhost:8080/cliente/{codigo}
    @ApiOperation(value = "Atualizar um Cliente",
            nickname = "atualizarCliente")
    @PutMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable(name = "codigo") Long codigo,
                                                        @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente clienteAtualizado = clienteService.atualizar(codigo,
                ClienteMapper.converterParaEntidade(codigo, clienteRequestDTO));

        return ResponseEntity.ok(ClienteMapper.converterParaClienteDTO(clienteAtualizado));
    }

}
