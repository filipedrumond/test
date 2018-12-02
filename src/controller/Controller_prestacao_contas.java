/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import model.Model_funcionario;
import model.Model_prestacao_contas;

/**
 *
 * @author filip
 */
public class Controller_prestacao_contas {
    public Model_prestacao_contas instancia;

    
    public Controller_prestacao_contas() {
        instancia = new Model_prestacao_contas();
    } 
    public String listar_entre_datas(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim){
        try{
            Model_prestacao_contas[] result = instancia.listar_todos_entre_datas(dados_ini, dados_fim);
            return tabelar(result);
        }catch(SQLException e ){
            return "ERRO AO PESQUISAR";
        }
    }
    public String gerar_relatorio_reembolso(Model_funcionario dados,Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim){
        try{
            Model_prestacao_contas[] result = instancia.gerar_relatorio(dados,dados_ini,dados_fim);
            return tabelar(result)+"\nTotal ::::"+calcular_total(result);
        }catch(SQLException e ){
            return "ERRO AO PESQUISAR";
        }
    }
    
    
    
    public String listar_entre_datas_sem_cartao(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim){
        try{
            Model_prestacao_contas[] result = instancia.listar_todos_entre_datas_sem_cartao(dados_ini, dados_fim);
            return tabelar(result);
        }catch(SQLException e ){
            return "ERRO AO PESQUISAR";
        }
    }
        public String listar_entre_datas_com_cartao(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim){
        try{
            Model_prestacao_contas[] result = instancia.listar_todos_entre_datas_com_cartao(dados_ini, dados_fim);
            return tabelar(result);
        }catch(SQLException e ){
            return "ERRO AO PESQUISAR";
        }
    }
    public String inserir(Model_prestacao_contas novo_registro,Model_funcionario usuario){
        try{
            boolean result = instancia.inserir(novo_registro);
            return "Atualizado com sucesso"; 
        }catch(SQLException e){      
            return "ERRO AO ATUALIZAR";   
        }
    }
    public String listar_aprovados_chefe(Model_funcionario chefe) throws SQLException{
        try{
            Model_prestacao_contas[] result = instancia.listar_aprovacao_chefe(chefe);
            return tabelar(result);
        }catch(SQLException e){
            return "ERRO DESCONHECIDO "+ e;
        }
    }
    
       public float calcular_total(Model_prestacao_contas[] dados){
        float retorno =0;
        if(dados != null){
            for(Model_prestacao_contas resultado : dados){
                if(resultado != null){
                    retorno += resultado.valor;
                }
            }
        }
        return retorno;
    }
    public String tabelar(Model_prestacao_contas[] dados){
        String retorno ="|- NOME FUNC -|- TIPO PREST -|- NOME CARTAO -|- TIPO DESPESA -|- STATUS -|- VALOR -|"
                + "- DATA -|- DESCRICAO -|- APROVADO POR -|";
        if(dados != null){
            for(Model_prestacao_contas resultado : dados){
                if(resultado != null){
                    if(resultado.nome_impresso_no_cartao == null){
                        resultado.nome_impresso_no_cartao = "S/ cartao";
                    }
                    //System.out.println(resultado.valor);
                    retorno += "\n| "+resultado.funcionario+" | "+resultado.tipo_prestacao+" | "+resultado.nome_impresso_no_cartao+" | "
                            +resultado.tipo_despesa+" | "+resultado.tipo_aprovacao+" | "+resultado.valor+" | "
                            +resultado.data+" | "+resultado.descricao+" | "+resultado.chefe+" |";
                }
            }
        }
        return retorno;
    }
}
