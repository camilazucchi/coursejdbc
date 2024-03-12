package application;

import db.DB;
import db.DbIntegrityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.*;

/* Demo: Deletar dados
* Checklist:
* (✓) Criar DbIntegrityException
* (✓) Tratar a exceção de integridade referencial */
public class DeleteData {
    public static void main(String[] args) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = DB.getConnection();
            String sql = "DELETE FROM department WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 2);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}