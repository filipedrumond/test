package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;
import java.util.Random;
/*                                      TESTE
       Model_cartao instancia = new Model_cartao();
       Model_cartao insert = new Model_cartao();
       insert.id_funcionario = 1;
       insert.id_cartao = 3;
       
       boolean result = instancia.deletar(insert);
       System.out.println(result);
           
       Model_cartao[] resultados = instancia.listar_cartao_usuario(insert);
       for(Model_cartao resultado : resultados){
           if(resultado != null){
                System.out.println(resultado.nome_impresso + " "+ resultado.cod_seguranca);
           }
       }
       
    }


*/
public class Model_cartao {
    public int id_cartao;
    public String validade;
    public String nome_impresso;
    public String cod_seguranca;
    public int id_funcionario;
    private final Connection conn;
    public Model_cartao() {
       this.conn  = ConnectionDB.getConnection();
    }
    public Model_cartao[] listar_cartao_usuario(Model_cartao dados) throws SQLException{
        String query = "select * from cartoes where id_funcionario = ?;";
        try (PreparedStatement  pst = conn.prepareStatement(query)) {
            pst.setInt (1, dados.id_funcionario);
            int tamanho = 128;
            Model_cartao[] resultado = new Model_cartao[tamanho];
            int counter = 0;  
            try {
                ResultSet rs = pst.executeQuery();
                counter = 0;
                while (rs.next())
                {
                    resultado[counter]= new Model_cartao();
                    resultado[counter].validade = rs.getString("validade");
                    resultado[counter].nome_impresso = rs.getString("nome_impresso");
                    resultado[counter].cod_seguranca = rs.getString("cod_seguranca");
                    resultado[counter].id_funcionario = rs.getInt("id_funcionario");
                counter++;
                }
            }            
            catch(SQLException e){ System.out.println(e); }
            if(counter !=0){ return resultado;}
            else{ return null; }
        }
    }
    public boolean inserir(Model_cartao dados) throws SQLException{
        Model_funcionario instancia_func = new Model_funcionario();
        Model_funcionario dados_aux = instancia_func.selecionar(dados.id_funcionario);
        String query = "insert into cartoes(validade,nome_impresso,cod_seguranca,id_funcionario) values(?,?,?,?);";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            Random rand = new Random();
            int n = rand.nextInt(999) + 100;
            st.setString (1, "05/22");
            st.setString (2, dados_aux.nome);
            st.setString (3, String.valueOf(n));
            st.setInt (4, dados.id_funcionario);
            return st.execute();
        } 
    }
    public boolean deletar(Model_cartao dados) throws SQLException{
        String query = "delete from cartoes where id_cartao = ?;";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setInt (1, dados.id_cartao);
            return st.execute();
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
        public boolean atualizar_por_funcionario(Model_cartao dados) throws SQLException{
        String query = "update cartoes set validade = ?,nome_impresso = ?,cod_seguranca = ? where id_funcionario = ?;";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setString (1, dados.validade);
            st.setString (2, dados.nome_impresso);
            st.setString (3, dados.cod_seguranca);
            st.setInt (4, dados.id_funcionario);
            return st.execute();
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
}
