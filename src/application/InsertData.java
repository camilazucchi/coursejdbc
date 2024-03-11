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
        PreparedStatement preparedStatement = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            try (Connection connection = DB.getConnection()) {
                String sql = "INSERT INTO seller (name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, "Carl Grimes");
                preparedStatement.setString(2, "carl@gmail.com");
                preparedStatement.setDate(3, new java.sql.Date(sdf.parse("22/04/1999").getTime()));
                preparedStatement.setDouble(4, 3000.0);
                preparedStatement.setInt(5, 4);

                /* Quando há uma alteração que altera os dados, chamamos o método "executeUpdate()". */
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
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
            // Sempre fechamos a conexão por último.
            DB.closeConnection();
        }
    }
}
