/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filip
 */
public class Model_tipo_funcionario {
    
    public Connection conn;
    public Model_tipo_funcionario() {
       this.conn  = ConnectionDB.getConnection();
    }
    public int id_tipo_funcionario;
    public String tipo_funcionario;
       
    public Model_tipo_funcionario[] listar_todos() throws SQLException{
        String query = "select * from tipo_funcionario;";
        int tamanho = 128;
        Model_tipo_funcionario[] resultado = new Model_tipo_funcionario[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_tipo_funcionario();
                resultado[counter].id_tipo_funcionario = rs.getInt("id_tipo_funcionario");
                resultado[counter].tipo_funcionario = rs.getString("tipo_funcionario");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
        public Model_tipo_funcionario[] listar_disponiveis(Model_funcionario dados) throws SQLException{
        String query = "select * from tipo_funcionario where id_tipo_funcionario > ?;";
        try (PreparedStatement  pst = conn.prepareStatement(query)) {
            pst.setInt (1, dados.id_tipo_funcionario);
            int tamanho = 128;
            Model_tipo_funcionario[] resultado = new Model_tipo_funcionario[tamanho];
            int counter = 0;  
            try {
                ResultSet rs = pst.executeQuery();
                counter = 0;
                while (rs.next())
                {
                    resultado[counter]= new Model_tipo_funcionario();
                    resultado[counter].id_tipo_funcionario = rs.getInt("id_tipo_funcionario");
                    resultado[counter].tipo_funcionario = rs.getString("tipo_funcionario");
                counter++;
                }
            }            
            catch(SQLException e){ System.out.println(e); }
            if(counter !=0){ return resultado;}
            else{ return null; }
        }
    }
    
    
    public Model_tipo_funcionario[] selecionar_id(Model_funcionario dados) throws SQLException{
        String query = "select * from tipo_funcionario id_tipo_funcionario = ?;";
        try (PreparedStatement  pst = conn.prepareStatement(query)) {
            pst.setInt (1, dados.id_tipo_funcionario);
            int tamanho = 128;
            Model_tipo_funcionario[] resultado = new Model_tipo_funcionario[tamanho];
            int counter = 0;  
            try {
                ResultSet rs = pst.executeQuery();
                counter = 0;
                while (rs.next())
                {
                    resultado[counter]= new Model_tipo_funcionario();
                    resultado[counter].id_tipo_funcionario = rs.getInt("id_tipo_funcionario");
                    resultado[counter].tipo_funcionario = rs.getString("tipo_funcionario");
                counter++;
                }
            }            
            catch(SQLException e){ System.out.println(e); }
            if(counter !=0){ return resultado;}
            else{ return null; }
        }
    }
}
