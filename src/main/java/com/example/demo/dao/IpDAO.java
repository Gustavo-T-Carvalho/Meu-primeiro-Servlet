package com.example.demo.dao;

import com.example.demo.dto.IpDTO;
import com.example.demo.dto.PartidaDTO;
import com.example.demo.endPoints.CadastrarPartidaServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IpDAO {
    private Connection conexao;

    public IpDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<IpDTO> listar() throws SQLException {
        List<IpDTO> ipDTOList = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = conexao.prepareStatement("SELECT id, ip FROM ip_bloqueado");
        statement.execute();
        resultSet = statement.getResultSet();
        while (resultSet.next()){
            IpDTO ipDTO = new IpDTO();
            ipDTO.setId(resultSet.getLong(1));
            ipDTO.setIp(resultSet.getString(2));
            ipDTOList.add(ipDTO);
        }
        statement.close();

        return ipDTOList;
    }

}
