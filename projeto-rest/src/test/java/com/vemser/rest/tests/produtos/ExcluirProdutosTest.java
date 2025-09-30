package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.ProdutosConstants;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ExcluirProdutosTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaDeveExcluirProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.deveExcluirProdutoComSucesso();

        produtoClient.excluirProdutos(produto.getAuthorization(), produto.getId())
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/editar_produto.json"))
                ;
    }


    @Test
    public void testDeveExcluirProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.deveExcluirProdutoComSucesso();

        produtoClient.excluirProdutos(produto.getAuthorization(), produto.getId())
                .then()
                        .statusCode(200)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_EXCLUIR_COM_SUCESSO))
                ;
    }

    @Test
    public void testExcluirProdutoQueFazParteDeUmCarrinho(){

        Produto produto = ProdutoDataFactory.excluirProdutoQueFazParteDeUmCarrinho();

        produtoClient.excluirProdutos(produto.getAuthorization(), produto.getId())
                .then()
                        .statusCode(400)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_EXCLUIR_PRODUTO_COM_CARRINHO))
                        .body("idCarrinhos", notNullValue())
                ;
    }

    @Test
    public void testExcluirProdutoComIDInvalido(){

        Produto produto = ProdutoDataFactory.excluirProdutoComIDInvalido();

        produtoClient.excluirProdutos(produto.getAuthorization(), produto.getId())
                .then()
                        .statusCode(200)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_EXCLUIR_ID_INVALIDO))
                ;
    }
}
