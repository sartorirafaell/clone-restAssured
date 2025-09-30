package com.vemser.rest.client;

import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.ProdutosConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ProdutoClient extends BaseClient{

    public Response listarProdutos(Map<String, String> queryParams){

        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .queryParams(Optional.ofNullable(queryParams).orElse(Map.of()))
                .when()
                        .get(ProdutosConstants.ENDPOINT_PRODUTOS)
                ;
    }

    public Response listarProdutosPorID(String id){

        return
                given()
                        .spec(super.set())
                        .pathParam(ProdutosConstants.ENDPOINT_PRODUTOS_ID, id)
                .when()
                        .get(ProdutosConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }

    public Response editarProdutos(Produto produto, Map<String, String> produtoBody) {

        return
                given()
                        .spec(super.set())
                        .pathParam(ProdutosConstants.ID, produto.getId())
                        .header(ProdutosConstants.AUTHORIZATION, produto.getAuthorization())
                        .contentType(ContentType.JSON)
                        .body(produtoBody)
                .when()
                        .put(ProdutosConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }

    public Response excluirProdutos (String token, String id){

        return
                given()
                        .spec(super.set())
                        .header(ProdutosConstants.AUTHORIZATION, token)
                        .pathParam(ProdutosConstants.ID, id)
                .when()
                        .delete(ProdutosConstants.ENDPOINT_PRODUTOS_ID)
                ;
    }

    public Response cadastrarProdutos(Produto produto){

        return
                given()
                        .spec(super.set())
                        .header(ProdutosConstants.AUTHORIZATION, produto.getAuthorization())
                        .body(produto)
                .when()
                        .post(ProdutosConstants.ENDPOINT_PRODUTOS)
                ;
    }
}
