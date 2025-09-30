package com.vemser.rest.utils;

public class ProdutosConstants {

    public static final String ENDPOINT_PRODUTOS = "/produtos";
    public static final String ENDPOINT_PRODUTOS_ID = "/produtos/{_id}";
    public static final String ID = "_id";
    public static final String AUTHORIZATION = "Authorization";
    public static final String PRODUTOS = "produtos";
    public static final String NOME = "nome";
    public static final String PRECO = "preco";
    public static final String QUANTIDADE = "quantidade";
    public static final String MSG_NOME_EM_BRANCO = "nome não pode ficar em branco";
    public static final String MSG_PRECO_NEGATIVO = "preco deve ser um número positivo";
    public static final String MSG_PRECO_DEVE_SER_NUM = "preco deve ser um número";
    public static final String MSG_QUANTIDADE_MAIOR_OU_IGUAL_A_ZERO = "quantidade deve ser maior ou igual a 0";
    public static final String DESCRICAO = "descricao";
    public static final String MSG_DESCRICAO_EM_BRANCO = "descricao não pode ficar em branco";
    public static final String MESSAGE = "message";
    public static final String MSG_PRODUTO_COM_NOME_DUPLICADO = "Já existe produto com esse nome";
    public static final String MSG_TOKEN_INVALIDO_OU_VAZIO = "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais";
    public static final String MSG_TOKEN_DE_USUARIO_SEM_PERMISSAO = "Rota exclusiva para administradores";
    public static final String ID_PRODUTO_PARA_EDICOES_COM_ERRO = "K6leHdftCeOJj8BJ";
    public static final String MSG_CADASTRO_COM_SUCESSO = "Cadastro realizado com sucesso";
    public static final String MSG_ATUALIZAR_COM_SUCESSO = "Registro alterado com sucesso";
    public static final String MSG_EXCLUIR_COM_SUCESSO = "Registro excluído com sucesso";
    public static final String MSG_EXCLUIR_PRODUTO_COM_CARRINHO = "Não é permitido excluir produto que faz parte de carrinho";
    public static final String MSG_EXCLUIR_ID_INVALIDO = "Nenhum registro excluído";
    public static final String MSG_PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";

}
