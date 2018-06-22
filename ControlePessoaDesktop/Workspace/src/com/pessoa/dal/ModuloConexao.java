package com.pessoa.dal;

import java.sql.*;

public class ModuloConexao {
    
        public static Connection conector(){
            java.sql.Connection conn = null;
            // a linha abaixo chama o driver
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            // armazenando informações referentes ao banco
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=ControlePessoa";
            String user = "sa";
            String password = "123456";
            // Estabelecendo a conexão com o banco
    try{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                
        conn = DriverManager.getConnection(connectionUrl, user, password);
        return conn;        
                
    } catch(Exception ex){
        System.out.println(ex);
        return null;
    }}
        public static void main (String[]args){}}



