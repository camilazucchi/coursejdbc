package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* Demo: Atualizar dados */
public class UpdateData {
    public static void main(String[] args) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            // Estabelece conexão com o banco de dados:
            connection = DB.getConnection();
            /* Define uma instrução SQL para atualizar dados na tabela "seller". Neste caso, aumenta o salário base
            * ("basesalary") em um valor específico para todos os registros onde o "deparmentid" é igual a um valor
            * específico. O uso do "?" indica parâmetros que serão definidos posteriormente: */
            String sql = "UPDATE seller SET basesalary = basesalary + ? WHERE (departmentid = ?)";
            /* Prepara um comando SQL parametrizado usando o método "prepareStatement()" da conexão. Os valores dos
            * parâmetros são definidos usando os métodos "setDouble()" e "setInt()" do "PreparedStatement": */
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, 200.0);
            preparedStatement.setInt(2, 2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
