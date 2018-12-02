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
public class Model_tipo_prestacao {
    
    public Connection conn;
    public Model_tipo_prestacao() {
       this.conn  = ConnectionDB.getConnection();
    }
    public int id_tipo_prestacao;
    public String tipo_prestacao;
       
    public Model_tipo_prestacao[] listar_todos() throws SQLException{
        String query = "select * from tipo_prestacao;";
        int tamanho = 128;
        Model_tipo_prestacao[] resultado = new Model_tipo_prestacao[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_tipo_prestacao();
                resultado[counter].id_tipo_prestacao = rs.getInt("id_tipo_prestacao");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
}
