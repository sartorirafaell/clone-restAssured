package com.vemser.rest.data.factory;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Login;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.LoginConstants;
import com.vemser.rest.utils.UsuariosConstants;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LoginDataFactory {

    static UsuarioClient usuarioCliente = new UsuarioClient();
    static Faker faker = new Faker(new Locale("PT-BR"));
    static LoginClient loginClient = new LoginClient();

    public static Login realizarLoginDeUsuarioComSucesso(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = usuarioCliente.listarUsuarios(queryParams);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);

        Login usuarioParaLogin = new Login();
        usuarioParaLogin.setEmail(usuarios.get(0).getEmail());
        usuarioParaLogin.setPassword(usuarios.get(0).getPassword());

        return usuarioParaLogin;
    }

    public static Login realizarLoginComEmailInvalido(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = usuarioCliente.listarUsuarios(queryParams);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);

        Login usuarioParaLogin = new Login();
        usuarioParaLogin.setEmail(faker.internet().emailAddress());
        usuarioParaLogin.setPassword(usuarios.get(0).getPassword());

        return usuarioParaLogin;
    }

    public static Login realizarLoginComFormatacaoDeEmailInvalido(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = usuarioCliente.listarUsuarios(queryParams);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);

        Login usuarioParaLogin = new Login();
        usuarioParaLogin.setEmail(faker.internet().emailAddress().replace("@", ""));
        usuarioParaLogin.setPassword(usuarios.get(0).getPassword());

        return usuarioParaLogin;
    }

    public static Login realizarLoginComEmailValidoESenhaInvalida(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = usuarioCliente.listarUsuarios(queryParams);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);

        Login usuarioParaLogin = new Login();
        usuarioParaLogin.setEmail(usuarios.get(0).getEmail());
        usuarioParaLogin.setPassword(faker.internet().password());

        return usuarioParaLogin;
    }

    public static String loginDeAdmin(){

        Login login = new Login();
        login.setEmail(LoginConstants.USUARIO_ADMIN);
        login.setPassword(LoginConstants.PASSWORD_USUARIO_ADMIN);
        Response loginResponse = loginClient.logarUsuario(login);

        String token = loginResponse.jsonPath().getString(LoginConstants.AUTHORIZATION);

        return token;
    }

    public static String loginSemAdmin(){

        Login login = new Login();
        login.setEmail(LoginConstants.USUARIO_NORMAL);
        login.setPassword(LoginConstants.PASSWORD_USUARIO_NORMAL);
        Response loginResponse2 = loginClient.logarUsuario(login);

        String tokenSemAdmin = loginResponse2.jsonPath().getString(LoginConstants.AUTHORIZATION);

        return tokenSemAdmin;
    }
}
