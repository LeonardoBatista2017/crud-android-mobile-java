package com.example.helloworld;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;

    @Override
    public String toString(){
        return nome;
    }
}
