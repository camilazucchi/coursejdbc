package application;

import db.DB;
import db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* Demo: Inserir dados
 * Checklist:
 * (✓) Inserção simples com PreparedStatement
 * (✓) Inserção com recuperação de ID */
public class InsertData {
    public static void main(String[] args) {
        /* Declaração da variável "preparedStatement", que será usada para preparar e executar uma instrução SQL
         * parametrizada: */
        PreparedStatement preparedStatement = null;
        /* Criação de um objeto "SimpleDateFormat" para formatação de datas no formato "dd/MM/yyyy": */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Obtém conexão com o banco de dados utilizando o método "DB.getConnection()":
            try (Connection connection = DB.getConnection()) {
                // Declaração da string SQL que será usada para inserir dados na tabela "seller":
                String sql = "INSERT INTO seller (name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)";
                /* Prepara a instrução SQL parametrizada e indica que desejamos recuperar as chaves auto-geradas após a
                 * execução da instrução: */
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, "Carl Grimes");
                preparedStatement.setString(2, "carl@gmail.com");
                preparedStatement.setDate(3, new java.sql.Date(sdf.parse("22/04/1999").getTime()));
                preparedStatement.setDouble(4, 3000.0);
                preparedStatement.setInt(5, 4);

                /* Quando há uma alteração que altera os dados, chamamos o método "executeUpdate()"!
                * Executa a instrução SQL e retorna o número de linhas afetadas: */
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Recupera o conjunto de chaves auto-geradas após a execução da instrução SQL:
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    // Itera sobre o conjunto de chaves auto-geradas e imprime o ID correspondente:
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        System.out.println("Done! ID: " + id);
                    }
                } else {
                    System.out.println("No rows were affected.");
                }
            } catch (ParseException e) {
                throw new DbException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            // Sempre fechamos a conexão por último!
            DB.closeConnection();
        }
    }
}
