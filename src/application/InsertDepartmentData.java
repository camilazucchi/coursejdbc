package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertDepartmentData {
    public static void main(String[] args) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = DB.getConnection();
            String sql = "INSERT INTO department (id, name) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 6);
            preparedStatement.setString(2, "D2");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
