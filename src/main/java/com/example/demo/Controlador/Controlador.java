package com.example.demo.Controlador;

import com.example.demo.dao.IpDAO;
import com.example.demo.db.FabricaDeConexoes;
import com.example.demo.dto.IpDTO;
import com.example.demo.dto.MensagemDTO;
import com.example.demo.endPoints.CadastrarPartidaServlet;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Controlador {
    private String versao = "Versão 4";
    private static final Logger logger = Logger.getLogger(CadastrarPartidaServlet.class.getName());

    public void enviarResultado(HttpServletResponse response, MensagemDTO mensagemDTO) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        String jsonRetorno = gson.toJson(mensagemDTO);

        PrintWriter out = response.getWriter();

        out.println(jsonRetorno);

    }

    public void validarRequest(HttpServletRequest request, HttpServletResponse response, EndPointControlado endPoint) throws IOException{
        String ip = request.getRemoteAddr();
        Connection conexao = null;
        Boolean ipBloqueado = false;
        MensagemDTO mensagemDTO = new MensagemDTO();

        try{
            conexao = FabricaDeConexoes.getConexao();
            IpDAO ipDao = new IpDAO(conexao);
            List<IpDTO> ipDTOList = ipDao.listar();
            for(IpDTO ipDTO: ipDTOList){
                if(ipDTO.getIp().equals(ip)){
                    ipBloqueado = true;
                }
            }
        } catch (SQLException e) {
            ipBloqueado = true;
        }

        if(ipBloqueado){
            mensagemDTO.setErro("Ip bloqueado");
        } else {
            Contexto contexto = new Contexto();
            contexto.setConexao(conexao);
            contexto.setRequest(request);
            contexto.setIp(ip);
            mensagemDTO =  endPoint.executarCasoDeUso(contexto);
        }
        enviarResultado(response, mensagemDTO);
    };
}

//        response.setContentType("text/html");
//PrintWriter out = response.getWriter();
//
//        out.println("<html><body>");
//                out.println("<h1> Erro:" + mensagemDTO.getErro() + "</h1>");
//                Set<Map.Entry<String, String>> entrySet = mensagemDTO.getConteudo().entrySet();
//        out.println("Conteudo");
//        out.println("<ul>");
//        for (Map.Entry<String, String> item : entrySet) {
//        out.println("<li>" + item.getKey() + ":" + item.getValue() + "</li>");
//        }
//        out.println("</ul>");
//        out.println("<h1> Versao" + versao + "</h1>");
//        out.println("</body></html>");