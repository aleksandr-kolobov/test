import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass = "testtest";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            //statement.execute(";");
            ResultSet resultSet = statement.executeQuery(
              "SELECT course_name AS name, MIN(MONTH(subscription_date)), " +
                  "MAX(MONTH(subscription_date)), COUNT(*) FROM purchaselist GROUP BY name;");
            int i = 0;
            while (resultSet.next()) {
                i++;
                double first = Double.parseDouble(resultSet.getString(2));
                double last = Double.parseDouble(resultSet.getString(3));
                double amount = Double.parseDouble(resultSet.getString(4));
                double mid = (last - first) / amount;
                System.out.println("Среднее значение продаж в месяц для курса «" +
                        resultSet.getString(1) + "» = " + mid);
            }
            System.out.println(i + " записей");
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
