package com.example.demo.Controlador;

import com.example.demo.dto.MensagemDTO;

import java.sql.Connection;

public interface EndPointControlado {
    MensagemDTO executarCasoDeUso(Contexto contexto);
}
