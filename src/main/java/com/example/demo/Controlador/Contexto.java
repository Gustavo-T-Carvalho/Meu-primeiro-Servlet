package com.example.demo.Controlador;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;

public class Contexto {
    private Connection conexao;
    private HttpServletRequest request;
    private String ip;


    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
