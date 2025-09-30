package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuarioClient;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.UsuariosConstants;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


public class UsuarioDataFactory {

    private static final UsuarioClient usuarioClient = new UsuarioClient();
    static Faker faker = new Faker(new Locale("PT-BR"));
    static Random geradorBoolean = new Random();

    public static Usuario usuarioValido(){

        return novoUsuario();
    }

    public static Usuario usuarioSemEmail(){

        Usuario usuario = novoUsuario();
        usuario.setEmail(StringUtils.EMPTY);
        return usuario;
    }

    private static Usuario novoUsuario(){

        Usuario usuario = new Usuario();
        usuario.setNome(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setPassword(faker.internet().password());
        usuario.setAdministrador(String.valueOf(geradorBoolean.nextBoolean()));

        return usuario;
    }


    public static Usuario usuarioComEmailDuplicado(String emailDuplicado) {

        Usuario usuarioComEmailDuplicado = novoUsuario();
        usuarioComEmailDuplicado.setNome(faker.name().fullName());
        usuarioComEmailDuplicado.setEmail(emailDuplicado);
        usuarioComEmailDuplicado.setPassword(faker.internet().password());
        usuarioComEmailDuplicado.setAdministrador(String.valueOf(geradorBoolean.nextBoolean()));

        return usuarioComEmailDuplicado;
    }

    public static Usuario editarUsuarioComSucesso(){

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response responseCadastro = usuarioClient.cadastrarUsuarios(usuario)
                .then()
                        .extract()
                            .response()
                ;

        usuario.setId(responseCadastro.jsonPath().getString(UsuariosConstants.ID));
        usuario.setPassword(faker.internet().password());

       return usuario;
    }

    public static Usuario editarUsuarioComEmailJaCadastrado(){

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuario)
                .then()
                ;

        Usuario usuarioParaEdicao = UsuarioDataFactory.usuarioValido();

        Response responseCadastro = usuarioClient.cadastrarUsuarios(usuarioParaEdicao)
                .then()
                    .extract()
                        .response()
                ;

        usuarioParaEdicao.setId(responseCadastro.jsonPath().getString(UsuariosConstants.ID));
        usuarioParaEdicao.setEmail(usuario.getEmail());

        return usuarioParaEdicao;
    }

    public static Usuario editarUsuarioComIDEmBranco(){
        
        Usuario usuarioParaEdicao = UsuarioDataFactory.usuarioValido();

        usuarioClient.cadastrarUsuarios(usuarioParaEdicao)
                .then()
                ;

        usuarioParaEdicao.setId("");

        return usuarioParaEdicao;
    }

    public static Usuario editarUsuarioSemIDCadastrado(){

        Usuario usuarioSemIDCadastrado = new Usuario();
        usuarioSemIDCadastrado.setNome(faker.name().fullName());
        usuarioSemIDCadastrado.setEmail(faker.internet().emailAddress());
        usuarioSemIDCadastrado.setPassword(faker.internet().password());
        usuarioSemIDCadastrado.setAdministrador(String.valueOf(geradorBoolean.nextBoolean()));
        usuarioSemIDCadastrado.setId(String.valueOf(faker.random().nextInt()));

        return usuarioSemIDCadastrado;
    }

    public static Map<String, String> listarUsuariosComSucesso(){

        return Map.of();
    }

    public static Map<String, String> listarUsuarioPorNomeComSucesso() {

        UsuarioClient usuarioCliente = new UsuarioClient();

        Response response = usuarioCliente.listarUsuarios(null);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);


        return Map.of(UsuariosConstants.NOME, usuarios.get(0).getNome());
    }

    public static Map<String, String> listarUsuarioPorEmailComValoresNumericos(){

        return  Map.of(UsuariosConstants.EMAIL, "321");
    }

    public static Map<String, String> listarUsuariosPorEmailInvalido(){

        return Map.of(UsuariosConstants.EMAIL, "rafael.qa.com.br");
    }

    public static Usuario listarUsuarioPorIDComSucesso(){

        UsuarioClient usuarioCliente = new UsuarioClient();

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = usuarioCliente.listarUsuarios(queryParams);

        List<Usuario> usuarios = response.jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class);

        Usuario usuarioFiltrado = new Usuario();
        usuarioFiltrado.setId(usuarios.get(0).getId());

        return usuarioFiltrado;
    }

    public static Usuario listarUsuarioPorIDSemCadastro (){

        Usuario usuario = new Usuario();
        usuario.setId(String.valueOf(faker.random().nextInt()));

        return usuario;
    }

    public static Usuario listarUsuarioPorIDSemPreencherCampoID(){

        Usuario usuario = new Usuario();
        usuario.setId(null);
        return usuario;
    }

    public static Usuario excluirUsuarioComSucesso(){

        Usuario usuario = UsuarioDataFactory.usuarioValido();

        Response response = usuarioClient.cadastrarUsuarios(usuario)
                .then()
                    .extract()
                        .response()
        ;

        usuario.setId(response.jsonPath().getString(UsuariosConstants.ID));

        return usuario;
    }

    public static Usuario excluirUsuarioComCarrinho(){

        Usuario usuario = new Usuario();
        usuario.setId("0uxuPY0cbmQhpEz1");

        return usuario;
    }

    public static Usuario excluirUsuarioComFormatoDeIDInvalido(){

        Usuario usuario = new Usuario();
        usuario.setId("aaaaabc");

        return usuario;
    }

}
