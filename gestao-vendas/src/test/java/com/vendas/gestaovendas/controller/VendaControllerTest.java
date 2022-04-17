package com.vendas.gestaovendas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendas.gestaovendas.config.ConfigTest;
import com.vendas.gestaovendas.dto.venda.model.ClienteVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.ItemVendaResponseDTO;
import com.vendas.gestaovendas.dto.venda.model.VendaResponseDTO;
import com.vendas.gestaovendas.entity.Cliente;
import com.vendas.gestaovendas.entity.ItemVenda;
import com.vendas.gestaovendas.entity.Produto;
import com.vendas.gestaovendas.entity.Venda;
import com.vendas.gestaovendas.factory.ClienteMockFactory;
import com.vendas.gestaovendas.factory.ProdutoMockFactory;
import com.vendas.gestaovendas.factory.VendaMockFactory;
import com.vendas.gestaovendas.service.VendaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigTest
public class VendaControllerTest {

    private static final String GET_LISTAR_VENDAS_POR_CLIENTE_PATH  = "/venda/cliente/%s";

    private static final Long       COD_VENDA           = 1L;
    private static final LocalDate  DATA_VENDA          = LocalDate.of(2022, 04, 27);
    private static final Long       COD_ITEM_VENDA      = 1L;
    private static final Integer    QUANTIDADE          = 1;
    private static final BigDecimal PRECO_VENDIDO       = new BigDecimal(3000);

    private static final Long    COD_CLIENTE            = 1L;
    private static final String  NOME_CLIENTE           = "Tony Stark";
    private static final String  TELEFONE_CLIENTE       = "(19)91234-5678";
    private static final Boolean ATIVO_CLIENTE          = true;
    private static final String  LOGRADOURO_CLIENTE     = "Rua 1";
    private static final Integer NUMERO_CLIENTE         = 100;
    private static final String  COMPLEMENTO_CLIENTE    = "Proximo Escola Rua 1";
    private static final String  BAIRRO_CLIENTE         = "Jardim Amanda";
    private static final String  CEP_CLIENTE            = "13188-000";
    private static final String  CIDADE_CLIENTE         = "Hortolândia";
    private static final String  ESTADO_CLIENTE         = "SP";

    private static final Long       ID_CATEGORIA        = 1L;
    private static final String     NOME_CATEGORIA      = "Tecnologia";

    private static final Long       ID_PRODUTO          = 1L;
    private static final String     DESCRICAO_PRODUTO   = "Notebook";
    private static final Integer    QUANTIDADE_PRODUTO  = 10;
    private static final BigDecimal PRECO_CUSTO_PRODUTO = new BigDecimal("2000");
    private static final BigDecimal PRECO_VENDA_PRODUTO = new BigDecimal("3000");
    private static final String     OBSERVACAO_PRODUTO  = "Notebook Dell Inspiron 15 polegadas";


    @MockBean
    private VendaService vendaServiceMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listarVendasPorCliente() throws Exception {
        // Criando Cliente
        final Cliente cliente =
                ClienteMockFactory.createCliente(COD_CLIENTE, NOME_CLIENTE, TELEFONE_CLIENTE, ATIVO_CLIENTE,
                        LOGRADOURO_CLIENTE, NUMERO_CLIENTE, COMPLEMENTO_CLIENTE, BAIRRO_CLIENTE, CEP_CLIENTE,
                        CIDADE_CLIENTE, ESTADO_CLIENTE);
        // Criando Produto
        final Produto produto =
                ProdutoMockFactory.createProduto(ID_PRODUTO, DESCRICAO_PRODUTO, QUANTIDADE_PRODUTO,
                        PRECO_CUSTO_PRODUTO, PRECO_VENDA_PRODUTO, OBSERVACAO_PRODUTO,
                        ID_CATEGORIA, NOME_CATEGORIA);
        // Criando Venda
        final Venda venda =
                VendaMockFactory.createVenda(COD_VENDA, DATA_VENDA, cliente);
        // Criando Item Venda
        final ItemVenda itemVenda =
                VendaMockFactory.createItemVenda(COD_ITEM_VENDA, QUANTIDADE, PRECO_VENDIDO, produto, venda);

        // Criando createItensVendaResponseDTO
        final ItemVendaResponseDTO itensVendaResponseDTO =
                VendaMockFactory.createItensVendaResponseDTO(itemVenda);
        // Criando createVendaResponseDTO
        final VendaResponseDTO vendaResponseDTO =
                VendaMockFactory.createVendaResponseDTO(COD_VENDA, DATA_VENDA, Arrays.asList(itensVendaResponseDTO));
        // Criando ClienteVendaResponseDTO
        final ClienteVendaResponseDTO clienteVendaResponseDTO =
                VendaMockFactory.createClienteVendaResponseDTO(NOME_CLIENTE, Arrays.asList(vendaResponseDTO));

        when(this.vendaServiceMock.listarVendasPorCliente(COD_CLIENTE)).thenReturn(clienteVendaResponseDTO);

        final MvcResult result = mvc.perform(get(String.format(GET_LISTAR_VENDAS_POR_CLIENTE_PATH, COD_CLIENTE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(vendaServiceMock, times(1)).listarVendasPorCliente(anyLong());
        assertThat(result.getResponse().getContentAsString(), is(createVendasJSON(clienteVendaResponseDTO)));
    }

    private String createVendasJSON(ClienteVendaResponseDTO clienteVendaResponseDTO) {
        return "{\"nome\":\"".concat(valueOf(clienteVendaResponseDTO.getNome()))+ "\""
             + ",\"vendaResponseDTO\":[{"
                + "\"codigo\":".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0).getCodigo()))
                + ",\"data\":\"".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0).getData())) + "\""
                + ",\"itemVendaResponseDTO\":[{"
                   + "\"codigo\":".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                                     .getItemVendaResponseDTO().get(0).getCodigo()))
                   + ",\"quantidade\":".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                                     .getItemVendaResponseDTO().get(0).getQuantidade()))
                   + ",\"precoVendido\":".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                                     .getItemVendaResponseDTO().get(0).getPrecoVendido()))
                   + ",\"codProduto\":".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                                     .getItemVendaResponseDTO().get(0).getCodProduto()))
                   + ",\"produtoDescricao\":\"".concat(valueOf(clienteVendaResponseDTO.getVendaResponseDTO().get(0)
                                     .getItemVendaResponseDTO().get(0).getProdutoDescricao())) + "\"}]}]}";
    }
}
