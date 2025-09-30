package com.vemser.rest.client;

import com.vemser.rest.model.Login;
import com.vemser.rest.utils.LoginConstants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient{

    public Response logarUsuario(Login login){

        return
                given()
                        .spec(super.set())
                        .body(login)
                .when()
                        .post(LoginConstants.ENDPOINT_LOGIN)
                ;
    }
}
