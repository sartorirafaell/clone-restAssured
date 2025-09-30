package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.UsuariosConstants;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ExcluirUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();


    @Test
    public void testSchemaDeveExcluirUsuarioComSucesso(){

        Usuario usuarioParaExcluir = UsuarioDataFactory.excluirUsuarioComSucesso();

        usuarioClient.excluirUsuarios(usuarioParaExcluir.getId())
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/excluir_usuario.json"))
                ;
    }

    @Test
    public void testDeveExcluirUsuarioComSucesso(){

        Usuario usuarioParaExcluir = UsuarioDataFactory.excluirUsuarioComSucesso();

        usuarioClient.excluirUsuarios(usuarioParaExcluir.getId())
                .then()
                        .statusCode(200)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EXCLUIR_COM_SUCESSO))
                ;
    }

    @Test
    public void testDeveExcluirUsuarioComCarrinho(){

        Usuario usuarioComCarrinho = UsuarioDataFactory.excluirUsuarioComCarrinho();

        usuarioClient.excluirUsuarios(usuarioComCarrinho.getId())
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_EXCLUIR_USUARIO_COM_CARRINHO))
                        .body("idCarrinho", notNullValue())
                ;
    }

    @Test
    public void testDeveExcluirUsuarioComFormatacaoInvalidaNoID(){

        Usuario usuarioComFormatoDeIDInvalido = UsuarioDataFactory.excluirUsuarioComFormatoDeIDInvalido();

        usuarioClient.excluirUsuarios(usuarioComFormatoDeIDInvalido.getId())
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ID_INVALIDO))
                ;
    }
}
