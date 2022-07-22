package com.example.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexoes {
    public static Connection getConexao() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/estudobanco", "", "");

        return conexao;
    }



}
