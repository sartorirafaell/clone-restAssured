package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;

import com.vemser.rest.utils.UsuariosConstants;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class EditarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();


    @Test
    public void testSchemaDeveAtualizarUsuarioComSucesso(){

        Usuario usuarioEditado = UsuarioDataFactory.editarUsuarioComSucesso();

        usuarioClient.editarUsuarios(usuarioEditado.getId(), usuarioEditado)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/editar_usuario.json"))
                ;
    }

    @Test
    public void testDeveAtualizarUsuarioComSucesso() {

        Usuario usuarioEditado = UsuarioDataFactory.editarUsuarioComSucesso();

       usuarioClient.editarUsuarios(usuarioEditado.getId(), usuarioEditado)
                .then()
                        .statusCode(200)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ATUALIZAR_COM_SUCESSO))
                ;
    }


    @Test
    public void testDeveAtualizarUsuarioComEmailJaCadastrado(){

        Usuario usuarioEditadoComEmailDuplicado = UsuarioDataFactory.editarUsuarioComEmailJaCadastrado();

       usuarioClient.editarUsuarios(usuarioEditadoComEmailDuplicado.getId(), usuarioEditadoComEmailDuplicado)
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EMAIL_JA_CADASTRADO))
                ;
    }

    @Test
    public void testDeveAtualizarUsuarioComIDEmBranco(){

        Usuario usuarioEditadoSemID = UsuarioDataFactory.editarUsuarioComIDEmBranco();

        usuarioClient.editarUsuarios(usuarioEditadoSemID.getId(), usuarioEditadoSemID)
                .then()
                        .statusCode(405)
                        .body(UsuariosConstants.MESSAGE,equalTo(UsuariosConstants.MSG_ID_OBRIGATORIO))
                ;
    }

    @Test
    public void testEditarUsuarioSemIDCadastradoEDeveCadastrarComoNovoUsuario(){

        Usuario usuarioEditadoSemIDCadastrado = UsuarioDataFactory.editarUsuarioSemIDCadastrado();

       Response response = usuarioClient.editarUsuarios(usuarioEditadoSemIDCadastrado.getId(), usuarioEditadoSemIDCadastrado)
                .then()
                        .statusCode(201)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_CADASTRO_COM_SUCESSO))
                        .body(UsuariosConstants.ID, notNullValue())
                        .extract()
                                .response();
                ;
        usuarioClient.excluirUsuarios(response.jsonPath().getString(UsuariosConstants.ID));
    }
}
