import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    //Adding Employees
     static void   addEmployee(Connection cons , Scanner sc) throws SQLException {
       String sqlQuery ="INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";

         sc.nextLine();
         System.out.print("Enter name: ");
         String name = sc.nextLine();

         System.out.print("Enter position: ");
         String position = sc.nextLine();

         System.out.print("Enter salary: ");
         double salary = sc.nextDouble();
         sc.nextLine();


         PreparedStatement statement =cons.prepareStatement(sqlQuery);
         try {
             statement.setString(1, name);
             statement.setString(2, position);
             statement.setDouble(3, salary);
             statement.executeUpdate();
             System.out.println("Employee added successfully...!");
         } catch (SQLException e) {
             System.err.println("Error adding employee: " + e.getMessage());
         }
    }

    static void viewEmployees(Connection cons) throws SQLException {
       String sqlQuery ="SELECT * FROM demo.employees";

        try (Statement stmt = cons.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Position: %s | Salary: %.2f%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("position"), rs.getDouble("salary"));
            }
        }  catch (SQLException e) {
            System.err.println("Error viewing employees: " + e.getMessage());
        }
    }

    static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter employee ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();

        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Salary updated...!");
            } else {
                System.out.println("Employee not found with : ."+id);
            }
        }  catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter employee ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Salary deleted...!");
            } else {
                System.out.println("Employee not found with : ."+id);
            }
        }  catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
        }
    }
}