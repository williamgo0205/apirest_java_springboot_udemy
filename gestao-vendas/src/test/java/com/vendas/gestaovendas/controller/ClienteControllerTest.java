package com.vendas.gestaovendas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendas.gestaovendas.config.ConfigTest;
import com.vendas.gestaovendas.dto.cliente.mapper.ClienteMapper;
import com.vendas.gestaovendas.dto.cliente.model.ClienteRequestDTO;
import com.vendas.gestaovendas.dto.cliente.model.ClienteResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.factory.ClienteMockFactory;
import com.vendas.gestaovendas.service.ClienteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigTest
public class ClienteControllerTest {

    private static final String GET_CLIENTE_LISTAR_TODOS_PATH      = "/cliente";
    private static final String GET_CLIENTE_LISTAR_POR_CODIGO_PATH = "/cliente/%s";
    private static final String POST_ClIENTE_SALVAR_PATH           = "/cliente";
    private static final String PUT_CLIENTE_ATUALIZAR_PATH         = "/cliente/%s";
    private static final String DELETE_CLIENTE_DELETAR_PATH        = "/cliente/%s";

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

    private static final String  CEP_COM_FORMATO_INVALIDO      = "Cep esta com formato invalido.";
    private static final String  TELEFONE_COM_FORMATO_INVALIDO = "Telefone esta com formato invalido.";
    private static final String  NOME_COM_TAMANHO_INVALIDO     = "Nome deve ter entre 3 e 50 caracteres.";
    private static final String  NOME_CAMPO_OBRIGATORIO        = "Nome e um campo obrigatorio.";

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
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        final ClienteResponseDTO segundoClienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_2, NOME_CLIENTE_2, TELEFONE_CLIENTE_2,
                        ATIVO_CLIENTE_2, LOGRADOURO_CLIENTE_2, NUMERO_CLIENTE_2, COMPLEMENTO_CLIENTE_2,
                        BAIRRO_CLIENTE_2, CEP_CLIENTE_2, CIDADE_CLIENTE_2, ESTADO_CLIENTE_2);
        final List<ClienteResponseDTO> clienteResponseDTOList =
                Arrays.asList(primeiroClienteResponseDTO, segundoClienteResponseDTO);

        // Create List Cliente
        final Cliente primeiroCliente = ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1,
                TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        final Cliente segundoCliente = ClienteMockFactory.createCliente(COD_CLIENTE_2, NOME_CLIENTE_2,
                TELEFONE_CLIENTE_2, ATIVO_CLIENTE_2, LOGRADOURO_CLIENTE_2, NUMERO_CLIENTE_2, COMPLEMENTO_CLIENTE_2,
                BAIRRO_CLIENTE_2, CEP_CLIENTE_2, CIDADE_CLIENTE_2, ESTADO_CLIENTE_2);
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
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        // Create List Cliente
        final Cliente cliente = ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

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
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        // Create List Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
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

    @Test
    public void salvarClienteSucesso() throws Exception {
        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.salvar(ClienteMapper.converterParaEntidade(createClienteRequestDTO)))
                .thenReturn(cliente);

        final MvcResult result = mvc.perform(post(String.format(POST_ClIENTE_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(clienteServiceMock, times(1)).salvar(any());
        assertThat(result.getResponse().getContentAsString(), is(createClienteJSON(clienteResponseDTO)));
    }

    @Test
    public void erroSalvarClienteTelefoneFormatoInvalido_ValidationPattern() throws Exception {
        // Formato Telefone correto (00)00000-0000
        String telefoneFormatoInvalido = "00000000000";

        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(NOME_CLIENTE_1, telefoneFormatoInvalido, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1, CEP_CLIENTE_1,
                        CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, telefoneFormatoInvalido,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, telefoneFormatoInvalido,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.salvar(ClienteMapper.converterParaEntidade(createClienteRequestDTO)))
                .thenReturn(cliente);

        final MvcResult result = mvc.perform(post(String.format(POST_ClIENTE_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString()).contains(TELEFONE_COM_FORMATO_INVALIDO);
    }

    @Test
    public void erroSalvarClienteCepFormatoInvalido_ValidationPattern() throws Exception {
        // Formato Cep correto 00000-000
        String cepFormatoInvalido = "00000000";

        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                        cepFormatoInvalido, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, cepFormatoInvalido, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, cepFormatoInvalido, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.salvar(ClienteMapper.converterParaEntidade(createClienteRequestDTO)))
                .thenReturn(cliente);

        final MvcResult result = mvc.perform(post(String.format(POST_ClIENTE_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString()).contains(CEP_COM_FORMATO_INVALIDO);
    }

    @Test
    public void erroSalvarClienteNomeComTamanhoInvalido_ValidationLength() throws Exception {
        // Tamanho do campo nome inválido, deve ter entre 3 e 50 caracteres
        String nomeInvalido = "W";

        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(nomeInvalido, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                        CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, nomeInvalido, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, nomeInvalido, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.salvar(ClienteMapper.converterParaEntidade(createClienteRequestDTO)))
                .thenReturn(cliente);

        final MvcResult result = mvc.perform(post(String.format(POST_ClIENTE_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString())
                .contains(NOME_COM_TAMANHO_INVALIDO);
    }

    @Test
    public void erroSalvarClienteNomeCampoObrigatorio_ValidationNotNull() throws Exception {
        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(null, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                        CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, null, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, null, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        when(clienteServiceMock.salvar(ClienteMapper.converterParaEntidade(createClienteRequestDTO)))
                .thenReturn(cliente);

        final MvcResult result = mvc.perform(post(String.format(POST_ClIENTE_SALVAR_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResponse().getContentAsString())
                .contains(NOME_CAMPO_OBRIGATORIO);
    }

    @Test
    public void deletarClienteSucesso() throws Exception {
        ClienteMockFactory.createClienteRequestDTO(NOME_CLIENTE_1, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                        CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        mvc.perform(delete(String.format(DELETE_CLIENTE_DELETAR_PATH, COD_CLIENTE_1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(clienteServiceMock, times(1)).deletar(anyLong());
    }

    @Test
    public void atualizarClienteSucesso() throws Exception {
        String nomeClienteAtualizado = "Tony Stark Atualizado";

        // Create ClienteRequestDTO
        final ClienteRequestDTO createClienteRequestDTO =
                ClienteMockFactory.createClienteRequestDTO(nomeClienteAtualizado, TELEFONE_CLIENTE_1, ATIVO_CLIENTE_1,
                        LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1, BAIRRO_CLIENTE_1,
                        CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, NOME_CLIENTE_1, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        final Cliente clienteAtualizado =
                ClienteMockFactory.createCliente(COD_CLIENTE_1, nomeClienteAtualizado, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);
        // Create ClienteResponseDTO
        final ClienteResponseDTO clienteResponseDTO =
                ClienteMockFactory.createClienteResponseDTO(COD_CLIENTE_1, nomeClienteAtualizado, TELEFONE_CLIENTE_1,
                        ATIVO_CLIENTE_1, LOGRADOURO_CLIENTE_1, NUMERO_CLIENTE_1, COMPLEMENTO_CLIENTE_1,
                        BAIRRO_CLIENTE_1, CEP_CLIENTE_1, CIDADE_CLIENTE_1, ESTADO_CLIENTE_1);

        doReturn(clienteAtualizado)
                .when(clienteServiceMock).atualizar(cliente.getCodigo(),
                        ClienteMapper.converterParaEntidade(cliente.getCodigo(), createClienteRequestDTO));


        final MvcResult result = mvc.perform(put(String.format(PUT_CLIENTE_ATUALIZAR_PATH, cliente.getCodigo()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createClienteRequestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(clienteServiceMock, times(1)).atualizar(anyLong(), any());
        assertThat(result.getResponse().getContentAsString(), is(createClienteJSON(clienteResponseDTO)));
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
                 + "{\"logradouro\":\"".concat(valueOf(clienteResponseDTOList.get(0)
                            .getEnderecoResponseDTO().getLogradouro())) + "\""
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
                 + "{\"logradouro\":\"".concat(valueOf(clienteResponseDTOList.get(1)
                            .getEnderecoResponseDTO().getLogradouro())) + "\""
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
