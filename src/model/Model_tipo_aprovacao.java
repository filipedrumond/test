/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filip
 */
public class Model_tipo_aprovacao {
    
    public Connection conn;
    public Model_tipo_aprovacao() {
       this.conn  = ConnectionDB.getConnection();
    }
    public int id_tipo_aprovacao;
    public String tipo_aprovacao;
       
    public Model_tipo_aprovacao[] listar_todos() throws SQLException{
        String query = "select * from tipo_aprovacao;";
        int tamanho = 128;
        Model_tipo_aprovacao[] resultado = new Model_tipo_aprovacao[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_tipo_aprovacao();
                resultado[counter].id_tipo_aprovacao = rs.getInt("id_tipo_aprovacao");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
}
