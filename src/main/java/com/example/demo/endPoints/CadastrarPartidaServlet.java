package com.example.demo.endPoints;

import com.example.demo.Controlador.Contexto;
import com.example.demo.Controlador.Controlador;
import com.example.demo.Controlador.EndPointControlado;
import com.example.demo.dao.PartidaDAO;
import com.example.demo.dto.MensagemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CadastrarPartidaServlet")
public class CadastrarPartidaServlet extends HttpServlet implements EndPointControlado {
    private static final Logger logger = Logger.getLogger(CadastrarPartidaServlet.class.getName());


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controlador controlador = new Controlador();
        controlador.validarRequest(request, response, this);
    }

    @Override
    public MensagemDTO executarCasoDeUso(Contexto contexto){
        Long partidaId = null;
        HttpServletRequest request = contexto.getRequest();
        MensagemDTO mensagemDTO = new MensagemDTO();
        Long uid = Long.valueOf((request.getParameter("uid")));
        String resultado = request.getParameter("resultado");
        String erro = null;
        try {
            PartidaDAO partidaDAO = new PartidaDAO(contexto.getConexao());
            partidaId = partidaDAO.inserir(resultado, uid, contexto.getIp());
            Map<String, String> conteudo = new HashMap<>();
            conteudo.put("partidaId", String.valueOf(partidaId));
            mensagemDTO.setConteudo(conteudo);
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "erro:", e);
            mensagemDTO.setErro("Erro interno ao cadastrar partida");
        }

        return mensagemDTO;
    }
}
