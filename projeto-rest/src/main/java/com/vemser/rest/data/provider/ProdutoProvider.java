package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutoDataFactory;
import com.vemser.rest.utils.ProdutosConstants;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class ProdutoProvider {

    public static Stream<Arguments> dadosInvalidosParaCadastroDeProduto() {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoComNomeEmBranco(), ProdutosConstants.NOME, ProdutosConstants.MSG_NOME_EM_BRANCO),
                Arguments.of(ProdutoDataFactory.produtoComPrecoNegativo(), ProdutosConstants.PRECO, ProdutosConstants.MSG_PRECO_NEGATIVO),
                Arguments.of(ProdutoDataFactory.produtoComQuantidadeNegativa(), ProdutosConstants.QUANTIDADE, ProdutosConstants.MSG_QUANTIDADE_MAIOR_OU_IGUAL_A_ZERO),
                Arguments.of(ProdutoDataFactory.produtoComDescricaoEmBranco(), ProdutosConstants.DESCRICAO, ProdutosConstants.MSG_DESCRICAO_EM_BRANCO)
        );
    }

    public static Stream<Arguments> dadosInvalidosParaEditarProduto() {
        return Stream.of(
                Arguments.of(ProdutoDataFactory.produtoEditadoComPrecoNegativo(), ProdutosConstants.PRECO, ProdutosConstants.MSG_PRECO_NEGATIVO, 400),
                Arguments.of(ProdutoDataFactory.produtoEditadoComQuantidadeNegativa(), ProdutosConstants.QUANTIDADE, ProdutosConstants.MSG_QUANTIDADE_MAIOR_OU_IGUAL_A_ZERO, 400),
                Arguments.of(ProdutoDataFactory.produtoComNomeDuplicado(), ProdutosConstants.MESSAGE, ProdutosConstants.MSG_PRODUTO_COM_NOME_DUPLICADO, 400),
                Arguments.of(ProdutoDataFactory.produtoComDescricaoEmBranco(), ProdutosConstants.DESCRICAO, ProdutosConstants.MSG_DESCRICAO_EM_BRANCO, 400),
                Arguments.of(ProdutoDataFactory.produtoComTokenInvalido(), ProdutosConstants.MESSAGE, ProdutosConstants.MSG_TOKEN_INVALIDO_OU_VAZIO, 401),
                Arguments.of(ProdutoDataFactory.produtoComTokenDeUsuarioSemPermissao(), ProdutosConstants.MESSAGE, ProdutosConstants.MSG_TOKEN_DE_USUARIO_SEM_PERMISSAO, 403)
        );
    }

}
