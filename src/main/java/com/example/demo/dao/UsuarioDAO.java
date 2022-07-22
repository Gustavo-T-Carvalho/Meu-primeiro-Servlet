package com.example.demo.dao;


import com.example.demo.dto.UsuarioDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(String nome, String email) throws SQLException {
        Long uid = null;
        PreparedStatement statement = conexao.prepareStatement("INSERT into usuario (nome, email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, nome);
        statement.setString(2, email);

        //        statement.setObject(1, nome);
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        uid = resultSet.getLong(1);
        statement.close();
        return uid;
    }

    public List<UsuarioDTO> listar() throws SQLException {
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = conexao.prepareStatement("SELECT uid, nome, email  FROM usuario ORDER BY uid");
        statement.execute();
        resultSet = statement.getResultSet();

        while (resultSet.next()){
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNome(resultSet.getString(2));
            usuarioDTO.setUid( resultSet.getLong(1));
            usuarioDTO.setEmail(resultSet.getString(3));
            usuarioDTOList.add(usuarioDTO);
        }
        statement.close();
        return usuarioDTOList;
    }

    public Integer deletar(Long uid) throws SQLException {
        PreparedStatement statement = conexao.prepareStatement("DELETE FROM usuario WHERE uid=?");
        statement.setLong(1, uid);
        Integer retornoSql = statement.executeUpdate();
        statement.close();
        return retornoSql;
    }

}
