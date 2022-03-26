package com.vendas.gestaovendas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendas.gestaovendas.config.ConfigTest;
import com.vendas.gestaovendas.dto.categoria.model.CategoriaResponseDTO;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.dto.endereco.model.EnderecoResponseDTO;
import com.vendas.gestaovendas.entity.Categoria;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.Endereco;
import com.vendas.gestaovendas.service.CategoriaService;
import com.vendas.gestaovendas.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigTest
public class ClienteControllerTest {

    private static final String GET_CLIENTE_LISTAR_TODOS_PATH      = "/cliente";
    private static final String GET_CLIENTE_LISTAR_POR_CODIGO_PATH = "/cliente/%s";

    private static final Long    COD_CLIENTE_1         = 1L;
    private static final String  NOME_CLIENTE_1        = "Tony Stark";
    private static final String  TELEFONE_CLIENTE_1    = "(19)91234-5678";
    private static final Boolean ATIVO_CLIENTE_1       = true;
    private static final String  LOGRADOURO_CLIENTE_1  = "Rua 1";
    private static final Integer NUMERO_CLIENTE_1      = 100;
    private static final String  COMPLEMENTO_CLIENTE_1 = "Proximo Escola Rua 1";
    private static final String  BAIRRO_CLIENTE_1      = "Jardim Amanda";
    private static final String  CEP_CLIENTE_1         = "13188-000";
    private static final String  CIDADE_CLIENTE_1      = "Hortolandia";
    private static final String  ESTADO_CLIENTE_1      = "SP";

    private static final Long    COD_CLIENTE_2         = 1L;
    private static final String  NOME_CLIENTE_2        = "Peter Parker";
    private static final String  TELEFONE_CLIENTE_2    = "(19)98765-4321";
    private static final Boolean ATIVO_CLIENTE_2       = true;
    private static final String  LOGRADOURO_CLIENTE_2  = "Rua 2";
    private static final Integer NUMERO_CLIENTE_2      = 200;
    private static final String  COMPLEMENTO_CLIENTE_2 = "Proximo Escola Rua 2";
    private static final String  BAIRRO_CLIENTE_2      = "Jardim Amanda 2";
    private static final String  CEP_CLIENTE_2         = "13188-001";
    private static final String  CIDADE_CLIENTE_2      = "Hortolandia";
    private static final String  ESTADO_CLIENTE_2      = "SP";

    @MockBean
    private ClienteService clienteServiceMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarTodos() throws Exception {
        // Create List ClienteResponseDTO
        final ClienteResponseDTO primeiroClienteResponseDTO =
                createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        final ClienteResponseDTO segundoClienteResponseDTO =
                createClienteResponseDTO(COD_CLIENTE_2, NOME_CLIENTE_2, TELEFONE_CLIENTE_2, ATIVO_CLIENTE_2,
                        LOGRADOURO_CLIENTE_2, NUMERO_CLIENTE_2, COMPLEMENTO_CLIENTE_2, BAIRRO_CLIENTE_2, CEP_CLIENTE_2,
                        CIDADE_CLIENTE_2, ESTADO_CLIENTE_2);
        final List<ClienteResponseDTO> clienteResponseDTOList =
                Arrays.asList(primeiroClienteResponseDTO, segundoClienteResponseDTO);

        // Create List Cliente
        final Cliente primeiroCliente = createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        final Cliente segundoCliente = createCliente(COD_CLIENTE_2, NOME_CLIENTE_2, TELEFONE_CLIENTE_2, ATIVO_CLIENTE_2,
                LOGRADOURO_CLIENTE_2, NUMERO_CLIENTE_2, COMPLEMENTO_CLIENTE_2, BAIRRO_CLIENTE_2, CEP_CLIENTE_2,
                CIDADE_CLIENTE_2, ESTADO_CLIENTE_2);
        List<Cliente> clienteList = Arrays.asList(primeiroCliente, segundoCliente);

        when(this.clienteServiceMock.listarTodos()).thenReturn(clienteList);

        final MvcResult result = mvc.perform(get(String.format(GET_CLIENTE_LISTAR_TODOS_PATH))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(clienteServiceMock, times(1)).listarTodos();
        assertThat(result.getResponse().getContentAsString(), is(createListClienteJSON(clienteResponseDTOList)));
    }

    @Test
    public void listarPorCodigoRetornoSucesso_HttpStatus_200() throws Exception {
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        // Create List Cliente
        final Cliente cliente = createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.buscarPorCodigo(COD_CLIENTE_1)).thenReturn(Optional.of(cliente));

        final MvcResult result = mvc.perform(get(String.format(GET_CLIENTE_LISTAR_POR_CODIGO_PATH, COD_CLIENTE_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(clienteServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(createClienteJSON(clienteResponseDTO)));
    }

    @Test
    public void listarPorCodigoRetornoErroNotFound_HttpStatus_404() throws Exception {
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        // Create List Cliente
        final Cliente cliente = createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.buscarPorCodigo(COD_CLIENTE_1)).thenReturn(Optional.empty());

        final MvcResult result = mvc.perform(get(String.format(GET_CLIENTE_LISTAR_POR_CODIGO_PATH, COD_CLIENTE_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(clienteServiceMock, times(1)).buscarPorCodigo(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(""));
    }

    private Cliente createCliente(Long codigo, String nome, String telefone, Boolean ativo, String logradouro,
                                  Integer numero, String complemento, String bairro, String cep, String cidade,
                                  String estado) {
        Cliente clienteCriado = new Cliente();
        clienteCriado.setCodigo(codigo);
        clienteCriado.setNome(nome);
        clienteCriado.setTelefone(telefone);
        clienteCriado.setAtivo(ativo);
        clienteCriado.setEndereco(createEndereco(logradouro, numero, complemento, bairro, cep, cidade, estado));
        return clienteCriado;
    }

    private Endereco createEndereco(String logradouro, Integer numero, String complemento, String bairro, String cep,
                                    String cidade, String estado) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCep(cep);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        return endereco;
    }

    private EnderecoResponseDTO createEnderecoResponseDTO(String logradouro, Integer numero, String complemento,
                                                          String bairro, String cep, String cidade, String estado) {
        return new EnderecoResponseDTO(logradouro, numero, complemento, bairro, cep, cidade, estado);
    }

    private ClienteResponseDTO createClienteResponseDTO(Long codigo, String nome, String telefone, Boolean ativo,
                                                        String logradouro, Integer numero, String complemento,
                                                        String bairro, String cep, String cidade, String estado) {
        return new ClienteResponseDTO(codigo, nome, telefone, ativo,
                createEnderecoResponseDTO(logradouro, numero, complemento, bairro,
                        cep, cidade, estado));
    }

    private String createClienteJSON(ClienteResponseDTO clienteResponseDTO) {
        return "{\"codigo\":".concat(valueOf(clienteResponseDTO.getCodigo()))
                + ",\"nome\":\"".concat(valueOf(clienteResponseDTO.getNome())) + "\""
                + ",\"telefone\":\"".concat(valueOf(clienteResponseDTO.getTelefone())) + "\""
                + ",\"ativo\":".concat(valueOf(clienteResponseDTO.getAtivo()))
                + ",\"enderecoResponseDTO\":"
                + "{\"logradouro\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getLogradouro())) + "\""
                + ",\"numero\":".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getNumero()))
                + ",\"complemento\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getComplemento())) + "\""
                + ",\"bairro\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getBairro())) + "\""
                + ",\"cep\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getCep())) + "\""
                + ",\"cidade\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getCidade())) + "\""
                + ",\"estado\":\"".concat(valueOf(clienteResponseDTO.getEnderecoResponseDTO().getEstado())) + "\"}}";
    }

    private String createListClienteJSON(List<ClienteResponseDTO> clienteResponseDTOList) {
        return "[{\"codigo\":".concat(valueOf(clienteResponseDTOList.get(0).getCodigo()))
              + ",\"nome\":\"".concat(valueOf(clienteResponseDTOList.get(0).getNome())) + "\""
              + ",\"telefone\":\"".concat(valueOf(clienteResponseDTOList.get(0).getTelefone())) + "\""
              + ",\"ativo\":".concat(valueOf(clienteResponseDTOList.get(0).getAtivo()))
              + ",\"enderecoResponseDTO\":"
                 + "{\"logradouro\":\"".concat(valueOf(clienteResponseDTOList.get(0).getEnderecoResponseDTO().getLogradouro())) + "\""
                 + ",\"numero\":".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getNumero()))
                 + ",\"complemento\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getComplemento())) + "\""
                 + ",\"bairro\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getBairro())) + "\""
                 + ",\"cep\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getCep())) + "\""
                 + ",\"cidade\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getCidade())) + "\""
                 + ",\"estado\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getEstado())) + "\"}}"
              + ",{\"codigo\":".concat(valueOf(clienteResponseDTOList.get(1).getCodigo()))
              + ",\"nome\":\"".concat(valueOf(clienteResponseDTOList.get(1).getNome())) + "\""
              + ",\"telefone\":\"".concat(valueOf(clienteResponseDTOList.get(1).getTelefone())) + "\""
              + ",\"ativo\":".concat(valueOf(clienteResponseDTOList.get(1).getAtivo()))
              + ",\"enderecoResponseDTO\":"
                 + "{\"logradouro\":\"".concat(valueOf(clienteResponseDTOList.get(1).getEnderecoResponseDTO().getLogradouro())) + "\""
                 + ",\"numero\":".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getNumero()))
                 + ",\"complemento\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getComplemento())) + "\""
                 + ",\"bairro\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getBairro())) + "\""
                 + ",\"cep\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getCep())) + "\""
                 + ",\"cidade\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getCidade())) + "\""
                 + ",\"estado\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getEstado())) + "\"}}]";
    }
}
