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
public class Model_tipo_despesa {
    
    public Connection conn;
    public Model_tipo_despesa() {
       this.conn  = ConnectionDB.getConnection();
    }
    public int id_tipo_despesa;
    public String tipo_despesa;
       
    public Model_tipo_despesa[] listar_todos() throws SQLException{
        String query = "select * from tipo_despesa;";
        int tamanho = 128;
        Model_tipo_despesa[] resultado = new Model_tipo_despesa[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_tipo_despesa();
                resultado[counter].id_tipo_despesa = rs.getInt("id_tipo_despesa");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
}
