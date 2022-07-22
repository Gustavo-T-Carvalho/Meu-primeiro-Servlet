package com.example.demo.endPoints;

import com.example.demo.Controlador.Contexto;
import com.example.demo.Controlador.Controlador;
import com.example.demo.Controlador.EndPointControlado;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.dto.MensagemDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DeletarServlet")
public class DeletarServlet extends HttpServlet implements EndPointControlado {
    private String message;
    public void init() {
        message = "Versão 3";
    }
    private static final Logger logger = Logger.getLogger(DeletarServlet.class.getName());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controlador controlador = new Controlador();
        controlador.validarRequest(request, response, this);
    }

    @Override
    public MensagemDTO executarCasoDeUso(Contexto contexto){
        Integer retornoDelete = null;
        HttpServletRequest request = contexto.getRequest();
        Long uid = Long.valueOf(request.getParameter("uid"));
        MensagemDTO mensagemDTO = new MensagemDTO();

            try {
                Connection conexao = contexto.getConexao();
                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                retornoDelete =  usuarioDAO.deletar(uid);
                Map<String, String> conteudo = new HashMap<>();
                conteudo.put("uid", String.valueOf(uid));
                conteudo.put("retornoDelete", String.valueOf(retornoDelete));
                mensagemDTO.setConteudo(conteudo);
                conexao.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "erro", e);
                mensagemDTO.setErro("Erro interno ao deletar jogador");
            }

        return mensagemDTO;
    }
}
