package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

        /*                          TESTE
        Model_funcionario instancia = new Model_funcionario();
        Model_funcionario insert = new Model_funcionario();
        insert.id_funcionario = 4;
        insert.nome = "TESTANDO";
        insert.cpf = "13162335612";
        insert.id_tipo_funcionario =3;
        insert.senha = "123qwe!@#";
        insert.id_chefe = 1;
        instancia.deletar(insert);
        
        Model_funcionario[] resultados = instancia.listar_todos();
        for(Model_funcionario resultado : resultados){
            if(resultado != null){
                System.out.println(resultado.nome);
            }
        }
        */


public class Model_funcionario {
    public Connection conn ;
    public Model_funcionario() {
       this.conn  = ConnectionDB.getConnection();
    }
    public int id_funcionario;
    public String nome;
    public String cpf;
    public int id_tipo_funcionario;
    public String tipo_funcionario;
    public float limite_aprovacao;
    public String senha;
    public int id_chefe;
    public Model_funcionario selecionar(int id_func_selecionado) throws SQLException{
        String query = "select tipo_func.tipo_funcionario,func.* from funcionarios func natural join tipo_funcionario tipo_func where func.id_funcionario = ?;";
        Model_funcionario resultado = new Model_funcionario();
        try (PreparedStatement  pst = conn.prepareStatement(query)) {
            pst.setInt (1, id_func_selecionado);
            try {
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    resultado.tipo_funcionario = rs.getString("tipo_funcionario");
                    resultado.id_funcionario = rs.getInt("id_funcionario");
                    resultado.nome = rs.getString("nome");
                    resultado.cpf = rs.getString("cpf");
                    resultado.id_tipo_funcionario = rs.getInt("id_tipo_funcionario");           
                    if(resultado.id_tipo_funcionario < 3){
                        resultado.limite_aprovacao = rs.getFloat("limite_aprovacao");
                    }
                    if(resultado.id_tipo_funcionario != 1){
                        resultado.id_chefe = rs.getInt("id_chefe");
                    }
                } 
            }            
            catch(SQLException e){ System.out.println(e); }
            if(resultado != null){ return resultado;}
            else{ return null; }
        }
    } 
    
    public Model_funcionario logar(Model_funcionario dados) throws SQLException{
        String query = "select tipo_fun.tipo_funcionario,func.* from funcionarios func natural join tipo_funcionario tipo_fun where func.nome = ? and func.senha = ?;";
        try (PreparedStatement  pst = conn.prepareStatement(query)) {
            pst.setString (1, dados.nome);
            pst.setString (2, dados.senha);
            Model_funcionario resultado = new Model_funcionario(); 
            try {
                ResultSet rs = pst.executeQuery();
                while (rs.next())
                {
                    resultado.tipo_funcionario = rs.getString("tipo_funcionario");
                    resultado.id_funcionario = rs.getInt("id_funcionario");
                    resultado.nome = rs.getString("nome");
                    resultado.cpf = rs.getString("cpf");
                    resultado.id_tipo_funcionario = rs.getInt("id_tipo_funcionario");           
                    if(resultado.id_tipo_funcionario < 3){
                        resultado.limite_aprovacao = rs.getFloat("limite_aprovacao");
                    }
                    if(resultado.id_tipo_funcionario != 1){
                        resultado.id_chefe = rs.getInt("id_chefe");
                    }
                }
            }            
            catch(SQLException e){ System.out.println(e); }
            if(resultado != null){ return resultado;}
            else{ return null; }
        }
    }    
    public boolean deletar(Model_funcionario dados)throws SQLException{
        String query = "delete from funcionarios where id_funcionario = ?";
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setInt (1, dados.id_funcionario);
            return st.execute();
        } 
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
    public boolean atualizar(Model_funcionario dados) throws SQLException{
        String query;
        switch (dados.id_tipo_funcionario) {
            case 1:
                query = "update funcionarios set nome = ? ,cpf = ? ,senha = ? ,limite_aprovacao = ? where id_funcionario = ?";
                break;
            case 2:
                query = "update funcionarios set nome = ? ,cpf = ? ,senha = ? ,limite_aprovacao = ? ,id_chefe = ? where id_funcionario = ?";
                break;
            default:
                query = "update funcionarios set nome = ? ,cpf = ?  ,senha = ? ,id_chefe = ? where id_funcionario = ?";
                break;
        }
        try (PreparedStatement  st = conn.prepareStatement(query)) {
            st.setString (1, dados.nome);
            st.setString (2, dados.cpf);
            st.setString (3, dados.senha);
            switch (dados.id_tipo_funcionario) {
                case 1:
                    st.setFloat (4, dados.limite_aprovacao);
                    st.setInt (5, dados.id_funcionario);
                    break;
                case 2:
                    st.setFloat (4, dados.limite_aprovacao);
                    st.setInt (5, dados.id_chefe);
                    st.setInt (6, dados.id_funcionario);
                    break;
                default:
                    st.setInt (4, dados.id_chefe);
                    st.setInt (5, dados.id_funcionario);
                    break;
            }
            return st.execute();
        } 
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
    
    public Model_funcionario[] listar_todos() throws SQLException{
        String query = "select tipo_fun.tipo_funcionario,func.* from funcionarios func natural join tipo_funcionario tipo_fun ;";
        int tamanho = 128;
        Model_funcionario[] resultado = new Model_funcionario[tamanho];
        int counter = 0;  
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            counter = 0;
            while (rs.next())
            {
                resultado[counter]= new Model_funcionario();
                resultado[counter].tipo_funcionario = rs.getString("tipo_funcionario");
                resultado[counter].id_funcionario = rs.getInt("id_funcionario");
                resultado[counter].nome = rs.getString("nome");
                resultado[counter].cpf = rs.getString("cpf");
                resultado[counter].id_tipo_funcionario = rs.getInt("id_tipo_funcionario");           
                if(resultado[counter].id_tipo_funcionario < 3){
                    resultado[counter].limite_aprovacao = rs.getFloat("limite_aprovacao");
                }
                if(resultado[counter].id_tipo_funcionario != 1){
                    resultado[counter].id_chefe = rs.getInt("id_chefe");
                }
                counter++;
            }
        }
        catch(SQLException e){ System.out.println(e); }
        if(counter !=0){ return resultado;}
        else{ return null; }
    }
    public boolean inserir(Model_funcionario dados) throws SQLException{
        String query;
        switch (dados.id_tipo_funcionario) {
            case 1:
                 query = "insert into funcionarios(nome,cpf,id_tipo_funcionario,senha,limite_aprovacao)values(?,?,?,?,?)";
                break;
            case 2:
                 query = "insert into funcionarios(nome,cpf,id_tipo_funcionario,senha,limite_aprovacao,id_chefe)values(?,?,?,?,?,?)";
                break;
            default:
                 query = "insert into funcionarios(nome,cpf,id_tipo_funcionario,senha,id_chefe)values(?,?,?,?,?)";
                break;
        }
            try (PreparedStatement  st = conn.prepareStatement(query)) {
                st.setString (1, dados.nome);
                st.setString (2, dados.cpf);
                st.setInt (3, dados.id_tipo_funcionario);
                st.setString (4, dados.senha);
                switch (dados.id_tipo_funcionario) {
                    case 1:
                        st.setFloat (5, dados.limite_aprovacao);
                        break;
                    case 2:
                        st.setFloat (5, dados.limite_aprovacao);
                        st.setInt (6, dados.id_chefe);
                        break;
                    default:
                        st.setInt (5, dados.id_chefe);
                        break;
                }
                return st.execute();
            } 
            catch(SQLException e){
                System.out.println(e);
                return false;
            }
    }
}