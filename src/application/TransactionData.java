package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/* Demo: Transações
 * API:
 * (✓) setAutoCommit(false)
 * É usado para controlar o comportamento de um autocommit em transações no banco de dados. O autocommit é um
 * comportamento em que cada operação de banco de dados é automaticamente confirmada após ser concluída.
 * (✓) commit()
 * É usado para confirmar uma transação no banco de dados.
 * (✓) rollback()
 * É usado para reverter uma transação no banco de dados. */
public class TransactionData {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DB.getConnection();
            // Não é para confirmar as operações automaticamente:
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            // Todos os vendedores pertencentes ao departamento 1 terão o salário base atualizado em 2000.90:
            int rows1 = statement.executeUpdate("UPDATE seller SET basesalary = 2000.90 WHERE departmentid = 1");
            // int x = 1;
            // if (x < 2) {
            //    throw new SQLException("Fake error");
            // }
            int rows2 = statement.executeUpdate("UPDATE seller SET basesalary = 3000.90 WHERE departmentid = 2");
            System.out.println("Rows 1: " + rows1 + " Rows 2: " + rows2);
            // Agora sim a transação terminou:
            connection.commit();
        } catch (SQLException e) {
            try {
                // Volta ao estado inicial do banco de dados:
                connection.rollback();
                throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Error while trying to rollback! Caused by: " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}