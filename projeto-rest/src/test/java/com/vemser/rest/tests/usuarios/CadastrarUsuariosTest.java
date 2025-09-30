package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;

import com.vemser.rest.utils.UsuariosConstants;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CadastrarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();


    @Test
    public void testSchemaDeveCadastrarUsuarioComSucesso() {

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/cadastro_de_usuario.json"))
                ;
    }

    @Test
    public void testDeveCadastrarUsuarioComSucesso() {

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
                .then()
                        .statusCode(201)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_CADASTRO_COM_SUCESSO))
                        .body(UsuariosConstants.ID, notNullValue())
                ;
    }

    @Test
    public void testDeveCadastrarUsuarioComEmailDuplicado() {

        Usuario usuarioExistente = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuarioExistente);


        Usuario usuarioComEmailDuplicado = UsuarioDataFactory.usuarioComEmailDuplicado(usuarioExistente.getEmail());

        usuarioClient.cadastrarUsuarios(usuarioComEmailDuplicado)
                .then()
                       .statusCode(400)
                       .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EMAIL_JA_CADASTRADO))
                ;
    }

    @Test
    public void testDeveCadastrarUsuarioComEmailEmBranco(){

        Usuario usuarioSemEmail = UsuarioDataFactory.usuarioSemEmail();

        usuarioClient.cadastrarUsuarios(usuarioSemEmail)
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.EMAIL, equalTo(UsuariosConstants.MSG_EMAIL_EM_BRANCO))
                ;
    }
}
