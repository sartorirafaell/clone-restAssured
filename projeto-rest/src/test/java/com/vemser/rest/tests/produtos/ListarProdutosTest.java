package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;

import com.vemser.rest.model.Produto;

import com.vemser.rest.utils.ProdutosConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ListarProdutosTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaListarProdutosComSucesso(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutosComSucesso();

        produtoClient.listarProdutos(queryParams)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/listar_produtos.json"))
                ;

    }

    @Test
    public void testListarProdutosComSucesso(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutosComSucesso();

        List<Produto> produtos = produtoClient.listarProdutos(queryParams)
                .then()
                        .statusCode(200)
                        .body(ProdutosConstants.PRODUTOS, notNullValue())
                        .body(ProdutosConstants.QUANTIDADE, notNullValue())
                        .extract()
                                .jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertFalse(produtos.isEmpty()),
                () -> Assertions.assertNotNull(produtos.get(0).getNome())
        );
    }

    @Test
    public void testListarProdutosPorNomeComSucesso(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutoPorNomeComSucesso();

        List<Produto> produtosFiltrados = produtoClient.listarProdutos(queryParams)
                .then()
                        .statusCode(200)
                        .body(ProdutosConstants.QUANTIDADE, greaterThanOrEqualTo(1))
                        .extract()
                                .jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertFalse(produtosFiltrados.isEmpty()),
                () -> Assertions.assertNotNull(produtosFiltrados.get(0).getId())
        );
    }

    @Test
    public void testListarProdutosPorIDInvalido(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutoPorIDInvalido();

        produtoClient.listarProdutos(queryParams)
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_PRODUTO_NAO_ENCONTRADO))
                ;

    }

    @Test
    public void testListarProdutosPorNomeInvalido(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutoPorNomeInvalido();

        produtoClient.listarProdutos(queryParams)
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_PRODUTO_NAO_ENCONTRADO))
                ;
    }

    @Test
    public void testListarProdutosPorPrecoComEntradaDeDadoInvalido(){

        Map<String, String> queryParams = ProdutoDataFactory.listarProdutoPorPrecoComEntradaDeDadoInvalido();

        produtoClient.listarProdutos(queryParams)
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.PRECO, equalTo(ProdutosConstants.MSG_PRECO_DEVE_SER_NUM))
                ;
    }

    @Test
    public void testSchemaListarProdutoPorIDComSucesso(){

        Produto produtoFiltrado = ProdutoDataFactory.listarProdutoPorIDComSucesso();

        produtoClient.listarProdutosPorID(produtoFiltrado.getId())
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"))
                ;

    }

    @Test
    public void testListarProdutoPorIDComSucesso(){

        Produto produtoFiltrado = ProdutoDataFactory.listarProdutoPorIDComSucesso();

        Produto response = produtoClient.listarProdutosPorID(produtoFiltrado.getId())
                .then()
                        .statusCode(200)
                        .extract()
                                .as(Produto.class)
                ;

        Assertions.assertAll(
                () -> Assertions.assertNotNull(response.getNome()),
                () -> Assertions.assertNotNull(String.valueOf(response.getQuantidade())),
                () -> Assertions.assertNotNull(response.getDescricao()),
                () -> Assertions.assertNotNull(String.valueOf(response.getQuantidade())),
                () ->Assertions.assertNotNull(response.getId())
        );
    }

    @Test
    public void testListarProdutosPorIDSemCadastro(){

        Produto produtoFiltrado = ProdutoDataFactory.listarProdutoPorIDSemCadastro();

        produtoClient.listarProdutosPorID(produtoFiltrado.getId())
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_PRODUTO_NAO_ENCONTRADO))
                ;
    }

    @Test
    public void testListarProdutosPorIDComCampoIDVazio(){

        Produto produtoFiltrado = ProdutoDataFactory.listarProdutoPorIDComCampoIDVazio();

        produtoClient.listarProdutosPorID(produtoFiltrado.getId())
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_PRODUTO_NAO_ENCONTRADO))
                ;
    }
}
