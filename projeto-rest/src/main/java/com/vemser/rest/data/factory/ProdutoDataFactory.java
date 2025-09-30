package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutoClient;
import com.vemser.rest.model.Produto;
import com.vemser.rest.utils.ProdutosConstants;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.vemser.rest.data.factory.LoginDataFactory.loginDeAdmin;
import static com.vemser.rest.data.factory.LoginDataFactory.loginSemAdmin;

public class ProdutoDataFactory {

    private static final ProdutoClient produtoClient = new ProdutoClient();
    static Faker faker = new Faker(new Locale("PT-BR"));


    public static Map<String, String> listarProdutosComSucesso(){

        return Map.of();
    }

    public static Map<String, String> listarProdutoPorNomeComSucesso() {

        Response response = produtoClient.listarProdutos(null);

        List<Produto> produtos = response.jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class);


        return Map.of(ProdutosConstants.NOME, produtos.get(0).getNome());
    }

    public static Map<String, String> listarProdutoPorIDInvalido(){

        return Map.of(ProdutosConstants.ID, String.valueOf(faker.random().nextInt()));
    }

    public static Map<String, String> listarProdutoPorNomeInvalido() {

        return Map.of(ProdutosConstants.NOME, faker.commerce().productName());
    }

    public static Map<String, String> listarProdutoPorPrecoComEntradaDeDadoInvalido() {

        return Map.of(ProdutosConstants.PRECO, faker.name().firstName());
    }

    public static Produto listarProdutoPorIDComSucesso(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = produtoClient.listarProdutos(queryParams);

        List<Produto> produtos = response.jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class);

        Produto produtoFiltrado = new Produto();
        produtoFiltrado.setId(produtos.get(0).getId());

        return produtoFiltrado;
    }

    public static Produto listarProdutoPorIDSemCadastro(){

        Produto produto = new Produto();
        produto.setId(String.valueOf(faker.random().nextInt()));

        return produto;
    }

    public static Produto listarProdutoPorIDComCampoIDVazio() {

        Produto produto = new Produto();
        produto.setId(String.valueOf(faker.random().nextInt()));

        return produto;
    }

    public static Produto editarProdutoComSucesso(){

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = produtoClient.listarProdutos(queryParams);

        List<Produto> produtos = response.jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class);

        Produto produtoFiltrado = produtos.get(0);

        String token = loginDeAdmin();

        produtoFiltrado.setAuthorization(token);

        return produtoFiltrado;
    }

    public static Map<String, String> produtoBody(Produto produto){

        Map<String, String> produtoMap = new HashMap<>();
        produtoMap.put("nome", produto.getNome());
        produtoMap.put("preco", String.valueOf(produto.getPreco()));
        produtoMap.put("descricao", produto.getDescricao());
        produtoMap.put("quantidade", String.valueOf(produto.getQuantidade()));

        return produtoMap;
    }

    public static Produto editarProdutoComIDInvalido(){

        String token = loginDeAdmin();

        Produto produto = new Produto();
        produto.setNome(faker.commerce().productName());
        produto.setPreco(faker.number().numberBetween(10, 1000));
        produto.setDescricao(faker.commerce().material());
        produto.setQuantidade(faker.number().numberBetween(1, 100));
        produto.setAuthorization(token);
        produto.setId(faker.commerce().promotionCode());

        return produto;
    }


    public static Produto deveExcluirProdutoComSucesso() {

        Map<String, String> queryParams = UsuarioDataFactory.listarUsuariosComSucesso();
        Response response = produtoClient.listarProdutos(queryParams);

        List<Produto> produtos = response.jsonPath().getList(ProdutosConstants.PRODUTOS, Produto.class);

        Produto produtoFiltrado = new Produto();
        produtoFiltrado.setId(produtos.get(0).getId());

        String token = loginDeAdmin();

        produtoFiltrado.setAuthorization(token);

        return produtoFiltrado;
    }

    public static Produto excluirProdutoQueFazParteDeUmCarrinho(){

        Produto produtoFiltrado = new Produto();
        produtoFiltrado.setId("BeeJh5lz3k6kSIzA");

        String token = loginDeAdmin();

        produtoFiltrado.setAuthorization(token);

        return produtoFiltrado;
    }

    public static Produto excluirProdutoComIDInvalido(){

        Produto produtoFiltrado = new Produto();
        produtoFiltrado.setId("BeeJh5lz3k6kSIzAAAC");

        String token = loginDeAdmin();

        produtoFiltrado.setAuthorization(token);

        return produtoFiltrado;
    }


    public static Produto deveCadastrarUmProdutoComSucesso() {

        String token = loginDeAdmin();
        Produto novoProduto = new Produto();

        novoProduto.setNome(faker.commerce().productName());
        novoProduto.setPreco(faker.number().numberBetween(10, 1000));
        novoProduto.setDescricao(faker.commerce().material());
        novoProduto.setQuantidade(faker.number().numberBetween(1, 100));
        novoProduto.setAuthorization(token);

        return novoProduto;
    }

    public static Produto deveCadastrarUmProdutoComErro(Produto produto) {

        String token = loginDeAdmin();
        Produto novoProduto = produto;

        novoProduto.setAuthorization(token);

        return novoProduto;
    }


    public static Produto produtoComNomeEmBranco(){

        Produto produto = new Produto(
                "",
                faker.number().numberBetween(10, 1000), faker.commerce().material(), faker.number().numberBetween(1, 100),
                null, null, null, null, 400
        );
        return produto;
    }

    public static Produto produtoComPrecoNegativo(){

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(-1, -1000), faker.commerce().material(), faker.number().numberBetween(1, 100),
                null, null, null, null,400
        );
        return produto;
    }

    public static Produto produtoComQuantidadeNegativa(){

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(10, 1000), faker.commerce().material(), faker.number().numberBetween(-1, -100),
                null, null, null, null,400
        );
        return produto;
    }

    public static Produto produtoComDescricaoEmBranco(){

        String token = loginDeAdmin();

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(10, 1000), "", faker.number().numberBetween(1, 100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, token, null, null,400
        );
        return produto;
    }

    public static Produto produtoComNomeDuplicado(){

        String token = loginDeAdmin();

        Produto produto = new Produto(
                "Logitech MX Vertical", faker.number().numberBetween(10, 1000), faker.commerce().material(), faker.number().numberBetween(1, 100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, token,
                null, null, 400
        );
        return produto;
    }

    public static Produto produtoComTokenInvalido(){

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(10, 1000), "teste", faker.number().numberBetween(1, 100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, "7yMiL1WuU",
                null, null, 401
        );
        return produto;
    }

    public static Produto produtoComTokenDeUsuarioSemPermissao(){

        String tokenSemAdmin = loginSemAdmin();

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(10, 1000), "teste", faker.number().numberBetween(1, 100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, tokenSemAdmin,
                null, null, 403
        );
        return produto;
    }

    public static Produto produtoEditadoComPrecoNegativo(){

        String token = loginDeAdmin();

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(-10, -1000), faker.commerce().material(), faker.number().numberBetween(1, 100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, token,
                null, null, 400
        );
        return produto;
    }

    public static Produto produtoEditadoComQuantidadeNegativa(){

        String token = loginDeAdmin();

        Produto produto = new Produto(
                faker.commerce().productName(), faker.number().numberBetween(10, 1000), faker.commerce().material(), faker.number().numberBetween(-1, -100),
                ProdutosConstants.ID_PRODUTO_PARA_EDICOES_COM_ERRO, token,
                null, null, 400
        );
        return produto;
    }

}
