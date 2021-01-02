import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection
{
    private static Connection connection;

    private static String url = "jdbc:mysql://localhost:3306/learn?UseTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    private static String dbUser = "root";
    private static String dbPass = "root";

    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection()
    {
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(url, dbUser, dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public  static void executeMultiInsert() throws SQLException {
        if (insertQuery.length() != 0) {
            String sql = "INSERT INTO voter_count(name, birthDate) " +
                    "VALUES" + insertQuery.toString();
            DBConnection.getConnection().createStatement().execute(sql);
        }
    }

    public static void countVoter(String name, String birthDay) throws SQLException
    {
        birthDay = birthDay.replace('.', '-');
        if (insertQuery.length() < 500000) {
            insertQuery.append((insertQuery.length() == 0 ? "" : ",") + "('" + name + "', '" + birthDay + "')");
        }
        else {
            insertQuery.append(", ('" + name + "', '" + birthDay + "')");
            executeMultiInsert();
            insertQuery = new StringBuilder();
        }


    }
}
