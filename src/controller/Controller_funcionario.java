/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import model.Model_funcionario;

/**
 *
 * @author filip
 */
public class Controller_funcionario {
    public Model_funcionario instancia;
    public Model_funcionario base;
    
    public Controller_funcionario() {
        instancia = new Model_funcionario();
        base = new Model_funcionario();
    }
    
    public Model_funcionario logar(Model_funcionario dados){
        Model_funcionario result = null;
        try{
            result = instancia.logar(dados);
            return result;
        }catch(SQLException e){
            System.out.println("e");
            return result;
        }
    }
    public String alterar_limite_aprovacao(Model_funcionario super_chefe,Model_funcionario chefe){
        if(super_chefe.id_funcionario == 1){
            if(super_chefe.limite_aprovacao > chefe.limite_aprovacao){
                try{
                    boolean result = instancia.atualizar(chefe);
                    return "Atualizado com sucesso";
                }catch(SQLException e){
                    return "ERRO AO ATUALIZAR";
                }
            }
            else{
                return "ERRO AO ATUALIZAR LIMITE INFERIOR AO PERMITIDO";
            }      
        }
        else{
            return "ERRO AO ATUALIZAR NAO PERMITIDO";
        }
    }
        
}
