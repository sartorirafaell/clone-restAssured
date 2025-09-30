package com.vemser.rest.tests.usuarios;
import com.vemser.rest.data.factory.UsuarioDataFactory;
import com.vemser.rest.model.Usuario;
import com.vemser.rest.utils.UsuariosConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.vemser.rest.client.UsuarioClient;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;



public class ListarUsuariosTest {

    private final UsuarioClient usuarioClient = new UsuarioClient();


    @Test
    public void testSchemaListarTodosUsuariosComSucesso() {

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();

        usuarioClient.listarUsuarios(queryParams)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/listar_usuarios.json"))
                ;
    }

    @Test
    public void testListarTodosUsuariosComSucesso() {


        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();

        List<Usuario> usuarios = usuarioClient.listarUsuarios(queryParams)
                .then()
                        .statusCode(200)
                        .body(UsuariosConstants.USUARIOS, notNullValue())
                        .body(UsuariosConstants.QUANTIDADE, notNullValue())
                        .extract()
                                .jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertFalse(usuarios.isEmpty()),
                () -> Assertions.assertNotNull(usuarios.get(0).getNome())
        );
    }

    @Test
    public void testListarUsuariosPorNomeComSucesso() {

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuarioPorNomeComSucesso();

        List<Usuario> usuariosFiltrados = usuarioClient.listarUsuarios(queryParams)
                .then()
                        .statusCode(200)
                        .body(UsuariosConstants.QUANTIDADE, greaterThanOrEqualTo(1))
                        .extract()
                                .jsonPath().getList(UsuariosConstants.USUARIOS, Usuario.class)
                ;

        Assertions.assertAll("response",
                () -> Assertions.assertFalse(usuariosFiltrados.isEmpty()),
                () -> Assertions.assertNotNull(usuariosFiltrados.get(0).getId()),
                () -> Assertions.assertEquals("Giulia Fontes", usuariosFiltrados.get(0).getNome())
        );
    }

    @Test
    public void testListarUsuariosPorEmailComValoresNumericos(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuarioPorEmailComValoresNumericos();

        usuarioClient.listarUsuarios(queryParams)
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.EMAIL, equalTo(UsuariosConstants.MSG_EMAIL_DEVE_SER_STRING))
                ;
    }

    @Test
    public void testListarUsuariosPorEmailInvalido(){

        Map<String, String> queryParams  = UsuarioDataFactory.listarUsuariosPorEmailInvalido();

        usuarioClient.listarUsuarios(queryParams)
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.EMAIL, equalTo(UsuariosConstants.MSG_EMAIL_INVALIDO))
                        .time(lessThan(3000L))
                ;
    }

    @Test
    public void testSchemaBuscarUsuarioPorIDComSucesso(){

        Usuario usuarioFiltrado = UsuarioDataFactory.listarUsuarioPorIDComSucesso();

        usuarioClient.listarUsuariosPorID(usuarioFiltrado.getId())
                .then()
                        .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"))
                ;
    }

    @Test
    public void testBuscarUsuarioPorIDComSucesso(){

        Usuario usuarioFiltrado = UsuarioDataFactory.listarUsuarioPorIDComSucesso();

        Usuario response = usuarioClient.listarUsuariosPorID(usuarioFiltrado.getId())
                .then()
                        .statusCode(200)
                        .extract()
                                .as(Usuario.class)
                ;

        Assertions.assertAll(
                () -> Assertions.assertNotNull(response.getNome()),
                () -> Assertions.assertNotNull(response.getEmail()),
                () -> Assertions.assertNotNull(response.getPassword()),
                () -> Assertions.assertNotNull(response.getAdministrador()),
                () ->Assertions.assertNotNull(response.getId())
        );
    }

    @Test
    public void testListarUsuarioPorIDSemCadastro(){

        Usuario usuario = UsuarioDataFactory.listarUsuarioPorIDSemCadastro();

        usuarioClient.listarUsuariosPorID(usuario.getId())
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_LISTAR_USUARIO_INVALIDO))
                ;
    }

    @Test
    public void testListarUsuarioPorIDSemPreencherCampoID(){

        Usuario usuario = UsuarioDataFactory.listarUsuarioPorIDSemPreencherCampoID();

        usuarioClient.listarUsuariosPorID(usuario.getId())
                .then()
                        .statusCode(400)
                        .body(UsuariosConstants.MESSAGE, equalTo(UsuariosConstants.MSG_ID_VAZIO))
                ;
    }
}
