package com.example.demo.endPoints;

import com.example.demo.Controlador.Contexto;
import com.example.demo.Controlador.Controlador;
import com.example.demo.Controlador.EndPointControlado;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.dto.MensagemDTO;
import com.example.demo.dto.UsuarioDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ListarServlet")
public class ListarServlet extends HttpServlet implements EndPointControlado {
    private static final Logger logger = Logger.getLogger(ListarServlet.class.getName());
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controlador controlador = new Controlador();
        controlador.validarRequest(request, response, this);
    }

    @Override
    public MensagemDTO executarCasoDeUso(Contexto contexto){
        List<UsuarioDTO> listaUsuarioDTO = null;

        Map<String, String> conteudo = new HashMap<>();
        MensagemDTO mensagemDTO = new MensagemDTO();

            try {
                Connection conexao = contexto.getConexao();
                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                listaUsuarioDTO = usuarioDAO.listar();
                for(UsuarioDTO usuarioDTO: listaUsuarioDTO){
                    conteudo.put(String.valueOf(usuarioDTO.getUid()), usuarioDTO.getNome());
                }
                mensagemDTO.setConteudo(conteudo);
                conexao.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "erro:", e);
                mensagemDTO.setErro("Erro interno ao listar usuarios");
            }

        return mensagemDTO;
    }
}
