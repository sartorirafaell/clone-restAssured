package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.ProdutosConstants;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CadastrarProdutosTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaDeveCadastrarUmProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.deveCadastrarUmProdutoComSucesso();

        Response response = produtoClient.cadastrarProdutos(produto)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/cadastro_de_produto.json"))
                        .extract().
                            response();
        ;
        produtoClient.excluirProdutos(produto.getAuthorization(), response.jsonPath().getString(ProdutosConstants.ID));
    }

    @Test
    public void testDeveCadastrarUmProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.deveCadastrarUmProdutoComSucesso();

        Response response = produtoClient.cadastrarProdutos(produto)
                .then()
                        .statusCode(201)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_CADASTRO_COM_SUCESSO))
                        .body(ProdutosConstants.ID, notNullValue())
                        .extract()
                            .response()
                ;
        produtoClient.excluirProdutos(produto.getAuthorization(), response.jsonPath().getString(ProdutosConstants.ID));
    }



    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#dadosInvalidosParaCadastroDeProduto")
    public void testDeveTentarCadastrarProdutoComDadosInvalidos(Produto produto, String tipoBody, String mensagemEsperada) {

        Produto cadastroDeProduto = ProdutoDataFactory.deveCadastrarUmProdutoComErro(produto);

        produtoClient.cadastrarProdutos(cadastroDeProduto)
                .then()
                        .statusCode(400)
                        .body(tipoBody, equalTo(mensagemEsperada))
                ;
    }


}
