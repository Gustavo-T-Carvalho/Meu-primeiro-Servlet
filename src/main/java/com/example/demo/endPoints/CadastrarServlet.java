package com.example.demo.endPoints;

import com.example.demo.Controlador.Contexto;
import com.example.demo.Controlador.Controlador;
import com.example.demo.Controlador.EndPointControlado;
import com.example.demo.dao.UsuarioDAO;
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

@WebServlet("/cadastrar-servlet")
public class CadastrarServlet extends HttpServlet implements EndPointControlado {

    private static final Logger logger = Logger.getLogger(CadastrarServlet.class.getName());



    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controlador controlador = new Controlador();
        controlador.validarRequest(request, response, this);
    }

    @Override
    public MensagemDTO executarCasoDeUso(Contexto contexto){
        HttpServletRequest request = contexto.getRequest();
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        Long uid = null;
        MensagemDTO mensagemDTO = new MensagemDTO();
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO(contexto.getConexao());
            uid = usuarioDAO.inserir(nome, email);
            Map<String, String> conteudo = new HashMap<>();
            conteudo.put("uid", String.valueOf(uid));
            conteudo.put("nome", nome);
            conteudo.put("email", email);
            mensagemDTO.setConteudo(conteudo);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "erro:", e);
            mensagemDTO.setErro("Erro interno ao cadastrar jogador");
        }

        return mensagemDTO;
    }
}