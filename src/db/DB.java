package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/* Nesta classe ficarão os métodos estáticos auxiliares, que, basicamente, servem para obter e fechar uma conexão com o
 * banco de dados. */
public class DB {
    private static Connection connection = null;

    /* Este método garante que apenas uma única instância de conexão seja criada e reutilizada sempre que necessário,
     * melhorando a eficiência e a gestão de recursos na aplicação. */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    /* Este método é responsável por fechar a conexão com o banco de dados, liberando os recursos associados a ela. */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /* Este método carrega as configurações do banco de dados a partir de um arquivo "db.properties" e retorna essas
     * configurações como um objeto "Properties". Isso é útil para configurar a conexão com o banco de dados de forma
     * flexível e externa ao código-fonte. */
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}