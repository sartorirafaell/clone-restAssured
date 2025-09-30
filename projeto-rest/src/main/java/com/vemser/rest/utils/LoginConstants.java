package com.vemser.rest.utils;

public class LoginConstants {

    public static final String ENDPOINT_LOGIN = "/login";
    public static final String MESSAGE = "message";
    public static final String EMAIL = "email";
    public static final String AUTHORIZATION = "authorization";
    public static final String MSG_LOGIN_COM_SUCESSO = "Login realizado com sucesso";
    public static final String USUARIO_NORMAL = Manipulation.getEmailUsuarioNormal();
    public static final String PASSWORD_USUARIO_NORMAL = Manipulation.getPasswordUsuarioNormal();
    public static final String USUARIO_ADMIN = Manipulation.getEmail();
    public static final String PASSWORD_USUARIO_ADMIN = Manipulation.getPwd();
    public static final String MSG_LOGIN_INVALIDO = "Email e/ou senha inválidos";
    public static final String MSG_EMAIL_INVALIDO = "email deve ser um email válido";
}
