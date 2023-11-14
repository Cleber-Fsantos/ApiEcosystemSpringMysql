package com.senai.apiecosystem.models;

public enum TipoModel {
    ADMIN("admin"),
    DOADOR("doador"),
    COLETOR("coletor");

    private String tipo;
    //Metodo construtor da Classe TipoModel
    TipoModel(String tipo){
        this.tipo = tipo;
    }
    public String getRole(){
        return tipo;
    }
}
