package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Produto {

    private String nome;
    private int preco;
    private String descricao;
    private int quantidade;

    @JsonProperty("_id")
    private String id;

    @JsonIgnore
    private String authorization;

    @JsonIgnore
    private String expectedMessage;

    @JsonIgnore
    private String tipoBody;

    @JsonIgnore
    private int statusCodigo;
}
