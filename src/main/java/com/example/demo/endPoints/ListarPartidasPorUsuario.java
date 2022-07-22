package com.example.demo.endPoints;

import com.example.demo.Controlador.Contexto;
import com.example.demo.Controlador.Controlador;
import com.example.demo.Controlador.EndPointControlado;
import com.example.demo.dao.PartidaDAO;
import com.example.demo.dto.MensagemDTO;
import com.example.demo.dto.PartidaDTO;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ListarPartidasPorUsuario")
public class ListarPartidasPorUsuario extends HttpServlet implements EndPointControlado {
    private static final Logger logger = Logger.getLogger(ListarPartidasPorUsuario.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Controlador controlador = new Controlador();
        controlador.validarRequest(request, response, this);
    }

    @Override
    public MensagemDTO executarCasoDeUso(Contexto contexto){
        Long uid = Long.valueOf(contexto.getRequest().getParameter("uid"));
        List<PartidaDTO> partidaDTOList = null;
        Map<String, String> conteudo = new HashMap<>();
        MensagemDTO mensagemDTO = new MensagemDTO();

            try{
                Connection conexao = contexto.getConexao();
                PartidaDAO partidaDAO = new PartidaDAO(conexao);
                partidaDTOList = partidaDAO.listar(uid);
                Gson gson = new Gson();

                String partidasJson = gson.toJson(partidaDTOList);
                conteudo.put("Partidas", partidasJson);
                mensagemDTO.setConteudo(conteudo);
            } catch(SQLException e) {
                logger.log(Level.SEVERE, "erro:", e);
                mensagemDTO.setErro("Erro interno ao listar partidas por usuario");
            }


        return mensagemDTO;

    }


}
