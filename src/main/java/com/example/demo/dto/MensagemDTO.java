package com.example.demo.dto;

import java.util.Map;

public class MensagemDTO {
    private String erro;
    private Map<String,String> conteudo;

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public Map<String, String> getConteudo() {
        return conteudo;
    }

    public void setConteudo(Map<String, String> conteudo) {
        this.conteudo = conteudo;
    }

}
