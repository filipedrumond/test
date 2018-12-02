package model;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;

        /*                         TESTE
        Model_prestacao_contas inst_prestacao_contas = new Model_prestacao_contas();
        Model_prestacao_contas update = new Model_prestacao_contas();
        update.id_prestacao_conta = 2;
        update.id_funcionario = 1;
        update.id_tipo_prestacao = 1;
        update.id_cartao = 1;
        update.id_tipo_despesa = 1;
        update.id_tipo_aprovacao = 1;
        update.valor = 35.05;
        update.data = new Date();
        update.descricao = "TESTANDO SAPOHA";
        boolean result = inst_prestacao_contas.deletar(update);
        System.out.println(result);
        
        Model_prestacao_contas[] resultados = inst_prestacao_contas.listar_todos();
        for(Model_prestacao_contas resultado : resultados){
            if(resultado != null){
                System.out.println(resultado.descricao+" "+resultado.tipo_aprovacao+" "+resultado.data);
            }
        }
        */


public class Model_prestacao_contas {
    private final Connection conn;
    public int id_prestacao_conta;
    
    public int id_funcionario;
    public String funcionario;    
    
    public int id_tipo_prestacao;
    public String tipo_prestacao;
    
    public int id_tipo_despesa;
    public String tipo_despesa;
    
    public int id_cartao;
    public String nome_impresso_no_cartao;
    
    public int id_tipo_aprovacao;
    public String tipo_aprovacao;
    
    public int id_chefe;
    public String chefe;
    
    public float valor;
    public Date data;
    public String descricao;
           
    public String pattern = "yyyy-MM-dd";
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public Model_prestacao_contas() {
       data = new Date();
       this.conn  = ConnectionDB.getConnection();
    }
    
        public Model_prestacao_contas[] gerar_relatorio(Model_funcionario dados,Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim) throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta where prest.data between ? and ? and prest.id_funcionario = ? order by prest.id_tipo_despesa;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString (1, simpleDateFormat.format(dados_ini.data));
            st.setString (2, simpleDateFormat.format(dados_fim.data));
            st.setInt(3,dados.id_funcionario);
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_prestacao_contas();
                resultado[counter].funcionario = rs.getString("nome");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                resultado[counter].id_cartao = rs.getInt("id_cartao");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                resultado[counter].valor = rs.getFloat("valor");
                resultado[counter].data = rs.getDate("data");
                resultado[counter].descricao = rs.getString("descricao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    
    public boolean alterar_status(Model_prestacao_contas dados) throws SQLException{
        String query;
        query = "update prestacao_contas set id_tipo_aprovacao = ? where id_prestacao_conta = ?";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setInt (1, dados.id_tipo_aprovacao);
            st.setInt (2, dados.id_prestacao_conta);
            return st.execute();
        } 
        catch(SQLException e){
            System.out.println(e);
            return false;
        }         
    }
    
        public Model_prestacao_contas[] listar_todos() throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_prestacao_contas();
                resultado[counter].funcionario = rs.getString("nome");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                resultado[counter].id_cartao = rs.getInt("id_cartao");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                resultado[counter].valor = rs.getFloat("valor");
                resultado[counter].data = rs.getDate("data");
                resultado[counter].descricao = rs.getString("descricao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    
    public Model_prestacao_contas[] listar_todos_entre_datas(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim) throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta where prest.data between ? and ?;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString (1, simpleDateFormat.format(dados_ini.data));
            st.setString (2, simpleDateFormat.format(dados_fim.data));
            ResultSet rs = st.executeQuery();
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_prestacao_contas();
                resultado[counter].funcionario = rs.getString("nome");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                resultado[counter].id_cartao = rs.getInt("id_cartao");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                resultado[counter].valor = rs.getFloat("valor");
                resultado[counter].data = rs.getDate("data");
                resultado[counter].descricao = rs.getString("descricao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    
        public Model_prestacao_contas[] listar_todos_entre_datas_sem_cartao(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim) throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta where prest.id_cartao is null and prest.data between ? and ?;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString (1, simpleDateFormat.format(dados_ini.data));
            st.setString (2, simpleDateFormat.format(dados_fim.data));
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_prestacao_contas();
                resultado[counter].funcionario = rs.getString("nome");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                resultado[counter].id_cartao = rs.getInt("id_cartao");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                resultado[counter].valor = rs.getFloat("valor");
                resultado[counter].data = rs.getDate("data");
                resultado[counter].descricao = rs.getString("descricao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
           
        public Model_prestacao_contas[] listar_todos_entre_datas_com_cartao(Model_prestacao_contas dados_ini,Model_prestacao_contas dados_fim) throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta where prest.id_cartao is not null and prest.data between ? and ?;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString (1, simpleDateFormat.format(dados_ini.data));
            st.setString (2, simpleDateFormat.format(dados_fim.data));
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_prestacao_contas();
                resultado[counter].funcionario = rs.getString("nome");
                resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                resultado[counter].id_cartao = rs.getInt("id_cartao");
                resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                resultado[counter].valor = rs.getFloat("valor");
                resultado[counter].data = rs.getDate("data");
                resultado[counter].descricao = rs.getString("descricao");
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    
        public Model_prestacao_contas[] listar_aprovacao_chefe(Model_funcionario chefe) throws SQLException{
        String query = "select func.nome,tp.tipo_prestacao,crt.nome_impresso,crt.id_cartao,td.tipo_despesa,ta.tipo_aprovacao,prest.valor,prest.data,prest.descricao \n" +
"			from prestacao_contas prest natural join funcionarios func natural join tipo_prestacao tp left join cartoes crt on crt.id_cartao = prest.id_cartao\n" +
"                       natural join tipo_despesa td natural join tipo_aprovacao ta where func.id_chefe = ? and ta.id_tipo_aprovacao = 1;";
        int tamanho = 128;
        Model_prestacao_contas[] resultado = new Model_prestacao_contas[tamanho];
        int counter = 0;  
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setInt (1, chefe.id_funcionario);
            ResultSet rs = st.executeQuery();
            counter = 0;
            try{
                while (rs.next())
                {
                    resultado[counter]= new Model_prestacao_contas();
                    resultado[counter].funcionario = rs.getString("nome");
                    resultado[counter].tipo_prestacao = rs.getString("tipo_prestacao");
                    resultado[counter].nome_impresso_no_cartao = rs.getString("nome_impresso");
                    resultado[counter].id_cartao = rs.getInt("id_cartao");
                    resultado[counter].tipo_despesa = rs.getString("tipo_despesa");
                    resultado[counter].tipo_aprovacao = rs.getString("tipo_aprovacao");
                    resultado[counter].valor = rs.getFloat("valor");
                    resultado[counter].data = rs.getDate("data");
                    resultado[counter].descricao = rs.getString("descricao");
                    resultado[counter].chefe =chefe.nome;
                    counter++;
                }
            }catch(SQLException e){
                return null;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    
    
    
    public boolean deletar(Model_prestacao_contas dados)throws SQLException{
        String query;
        query = "delete from prestacao_contas where id_prestacao_conta = ?";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setInt (1, dados.id_prestacao_conta);
            return st.execute();
        } 
        catch(SQLException e){
            System.out.println(e);
            return false;
        } 
        
    }
    
    public boolean inserir(Model_prestacao_contas dados) throws SQLException{
    String query = "insert into prestacao_contas(id_funcionario,id_tipo_prestacao,id_cartao,id_tipo_despesa,id_tipo_aprovacao,valor,data,descricao)values(?,?,?,?,?,?,?,?);";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
           
            st.setInt (1, dados.id_funcionario);
            st.setInt (2, dados.id_tipo_prestacao);
            st.setNull (3, dados.id_cartao);
            st.setInt (4, dados.id_tipo_despesa);
            st.setInt (5, dados.id_tipo_aprovacao);
            st.setFloat (6, dados.valor);
            st.setString (7, simpleDateFormat.format(dados.data));
            st.setString (8, dados.descricao);
            return st.execute();
        } 
    }
}
