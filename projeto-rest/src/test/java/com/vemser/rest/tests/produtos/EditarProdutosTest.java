package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.ProdutosConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class EditarProdutosTest {

    private final ProdutoClient produtoClient = new ProdutoClient();

    @Test
    public void testSchemaEditarProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.editarProdutoComSucesso();
        Map<String, String> produtoBody = ProdutoDataFactory.produtoBody(produto);

        produtoClient.editarProdutos(produto, produtoBody)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/editar_produto.json"))
                ;
    }

    @Test
    public void testEditarProdutoComSucesso(){

        Produto produto = ProdutoDataFactory.editarProdutoComSucesso();
        Map<String, String> produtoBody = ProdutoDataFactory.produtoBody(produto);
        produtoClient.editarProdutos(produto, produtoBody)
                .then()
                        .statusCode(200)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_ATUALIZAR_COM_SUCESSO))
                ;
    }


    @Test
    public void testEditarProdutoComIDInvalido(){

        Produto produto = ProdutoDataFactory.editarProdutoComIDInvalido();
        Map<String, String> produtoBody = ProdutoDataFactory.produtoBody(produto);

        produtoClient.editarProdutos(produto, produtoBody)
                .then()
                        .statusCode(201)
                        .body(ProdutosConstants.MESSAGE, equalTo(ProdutosConstants.MSG_CADASTRO_COM_SUCESSO))
                        .body(ProdutosConstants.ID, notNullValue())
                ;

    }



    @ParameterizedTest
    @MethodSource("com.vemser.rest.data.provider.ProdutoProvider#dadosInvalidosParaEditarProduto")
    public void testDeveTentarEditarProdutoComDadosInvalidos(Produto produto, String tipoBody, String mensagemEsperada, int codigoStatus){
        Map<String, String> produtoBody = ProdutoDataFactory.produtoBody(produto);
        produtoClient.editarProdutos(produto, produtoBody)
                .then()
                        .statusCode(codigoStatus)
                        .body(tipoBody, equalTo(mensagemEsperada))
                ;
    }
}
