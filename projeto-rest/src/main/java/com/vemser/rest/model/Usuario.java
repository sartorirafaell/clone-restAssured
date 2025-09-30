package com.vemser.rest.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    private String nome;
    private String email;
    private String password;
    private String administrador;

    @JsonProperty("_id")
    private String id;


}
