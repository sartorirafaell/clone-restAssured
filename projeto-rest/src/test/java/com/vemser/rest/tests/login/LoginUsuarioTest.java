package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.LoginConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.vemser.rest.client.UsuarioClient;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class LoginUsuarioTest {

    private final LoginClient loginClient = new LoginClient();

    @Test
    public void testSchemaDeveRealizarLoginComSucesso(){

        Login login = LoginDataFactory.realizarLoginDeUsuarioComSucesso();

        loginClient.logarUsuario(login)
            .then()
                    .body(matchesJsonSchemaInClasspath("schemas/logar_usuario.json"))
            ;
    }

    @Test
    public void testDeveRealizarLoginComSucesso(){

        Login login = LoginDataFactory.realizarLoginDeUsuarioComSucesso();

        loginClient.logarUsuario(login)
            .then()
                    .statusCode(200)
                    .body(LoginConstants.MESSAGE, equalTo(LoginConstants.MSG_LOGIN_COM_SUCESSO))
                    .body(LoginConstants.AUTHORIZATION, notNullValue())
            ;
    }

    @Test
    public void testDeveRealizarLoginComEmailInvalido(){

        Login login = LoginDataFactory.realizarLoginComEmailInvalido();

        loginClient.logarUsuario(login)
                .then()
                .statusCode(401)
                .body(LoginConstants.MESSAGE, equalTo(LoginConstants.MSG_LOGIN_INVALIDO))
        ;

        Assertions.assertNotNull(login.getPassword());
    }

    @Test
    public void testDeveRealizarLoginComFormatacaoDeEmailInvalido(){

        Login login = LoginDataFactory.realizarLoginComFormatacaoDeEmailInvalido();

        loginClient.logarUsuario(login)
                .then()
                .statusCode(400)
                .body(LoginConstants.EMAIL, equalTo(LoginConstants.MSG_EMAIL_INVALIDO))
        ;

    }

    @Test
    public void testDeveRealizarLoginComEmailValidoEComSenhaInvalida(){

        Login login = LoginDataFactory.realizarLoginComEmailValidoESenhaInvalida();

        loginClient.logarUsuario(login)
            .then()
                    .statusCode(401)
                    .body(LoginConstants.MESSAGE, equalTo(LoginConstants.MSG_LOGIN_INVALIDO))
            ;

    }
}
