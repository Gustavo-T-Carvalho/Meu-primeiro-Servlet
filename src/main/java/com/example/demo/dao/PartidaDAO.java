package com.example.demo.dao;

import com.example.demo.dto.PartidaDTO;
import com.example.demo.dto.UsuarioDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {
    private Connection conexao;


    public PartidaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Long inserir(String resultado, Long uid, String ip) throws SQLException {
        Long partidaId = null;
        PreparedStatement statement = conexao.prepareStatement("INSERT into partida (uid, resultado, ip) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, uid);
        statement.setString(2, resultado);
        statement.setString(3, ip);

        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        partidaId = resultSet.getLong(1);
        statement.close();
        return partidaId;
    }

    public List<PartidaDTO> listar(Long uid) throws SQLException{
        List<PartidaDTO> partidaDTOList = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = conexao.prepareStatement("SELECT resultado, horario, id FROM partida WHERE uid = ? ORDER BY horario ASC");
        statement.setLong(1,uid);
        statement.execute();
        resultSet = statement.getResultSet();
        while (resultSet.next()){
            PartidaDTO partidaDTO = new PartidaDTO();
            partidaDTO.setResultado(resultSet.getString(1));
            partidaDTO.setTimestamp(resultSet.getString(2));
            partidaDTO.setId(resultSet.getLong(3));
            partidaDTOList.add(partidaDTO);
        }
        statement.close();

        return partidaDTOList;
    }

}
