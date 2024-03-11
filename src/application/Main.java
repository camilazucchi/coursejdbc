package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Estabelece conexão com o banco de dados:
            connection = DB.getConnection();
            // Cria um objeto do tipo Statement a partir da conexão com o banco de dados:
            statement = connection.createStatement();
            // Executa um consulta SQL:
            resultSet = statement.executeQuery("SELECT * FROM department");

            /* O método "next()" do "ResultSet" move o cursor para a próxima linha no conjunto de resultados e retorna
            * "true" se houver mais linhas disponíveis para processamento. Portanto, este laço continuará enquanto
            * houver mais linhas no conjunto de resultados. */
            while (resultSet.next()) {
                /* Dentro do laço "while", estamos recuperando os valores das colunas "id" e "name" de cada linha do
                * conjunto de resultados e imprimindo-os no console.
                * - "resultSet.getInt("id")" retorna o valor da coluna "id" como um inteiro.
                * - "resultSet.getString("name")" retorna o valor da coluna "name" como uma string. */
                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}