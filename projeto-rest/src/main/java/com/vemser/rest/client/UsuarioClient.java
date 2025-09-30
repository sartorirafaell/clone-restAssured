package com.vemser.rest.client;

import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.LoginConstants;
import com.vemser.rest.utils.UsuariosConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class UsuarioClient extends BaseClient{

    public Response cadastrarUsuarios(Usuario usuario){

        return
            given()
                    .spec(super.set())
                    .body(usuario)
            .when()
                    .post(UsuariosConstants.ENDPOINT_USUARIOS)
            ;

    }

    public Response logarUsuario(Usuario usuario){

        return
            given()
                .spec(super.set())
                .body(usuario)
            .when()
                    .post(LoginConstants.ENDPOINT_LOGIN)
            ;
    }

    public Response editarUsuarios(String id, Usuario usuarioSemId) {
        usuarioSemId.setId(null);

        return
           given()
                    .pathParam(UsuariosConstants.ID, id)
                    .spec(super.set())
                    .body(usuarioSemId)
           .when()
                    .put(UsuariosConstants.ENDPOINT_USUARIOS_ID)
           ;
    }

    public Response listarUsuarios(Map<String, String> queryParams) {
        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .queryParams(queryParams != null ? queryParams : Map.of())
                .when()
                        .get(UsuariosConstants.ENDPOINT_USUARIOS)
                ;
    }

    public Response listarUsuariosPorID(String id){

        return
                given()
                        .spec(super.set())
                        .pathParam(UsuariosConstants.ID, id)
                .when()
                        .get(UsuariosConstants.ENDPOINT_USUARIOS_ID)
                ;
    }

    public Response excluirUsuarios(String id){

        return
           given()
                   .pathParam(UsuariosConstants.ID, id)
                   .spec(super.set())
           .when()
                   .delete(UsuariosConstants.ENDPOINT_USUARIOS_ID)
           ;
    }
}
