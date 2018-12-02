package view;
import controller.Controller_prestacao_contas;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Model_cartao;
import model.Model_funcionario;
import model.Model_prestacao_contas;
public class Principal {
    public static void main(String[] args) throws SQLException, ParseException {

       Controller_prestacao_contas instancia = new Controller_prestacao_contas();
       Model_prestacao_contas obj = new Model_prestacao_contas();
       Model_prestacao_contas obj2 = new Model_prestacao_contas();
       SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy/MM/dd");
       obj.data = dateformat3.parse("2018/01/01");
       
       obj2.data = dateformat3.parse("2018/12/31");
       Model_funcionario obj3 = new Model_funcionario();
       obj3.id_funcionario = 2;
       

        System.out.println(instancia.gerar_relatorio_reembolso(obj3,obj, obj2));
       
    }
}
