import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Employee {


    static String URL;
    static String USER;
    static String PASSWORD;

    //Database configuration
    static void loadDBConfig() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/db.properties")) {
            props.load(fis);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
            System.out.println("Database Connection is successfull...!");
        } catch (IOException e) {
            System.err.println("Error loading DB config: " + e.getMessage());
            System.exit(1);
        }
    }


    public static void main(String[] args) {

        loadDBConfig();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== Employee Database Menu ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> Main.addEmployee(conn, sc);
                    case 2 -> Main.viewEmployees(conn);
                    case 3 -> Main.updateEmployee(conn, sc);
                    case 4 -> Main.deleteEmployee(conn, sc);
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
