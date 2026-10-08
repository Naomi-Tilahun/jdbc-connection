import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/StudentsDB";
        String username = "root";
        String password = "Nina1914&19";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

            System.out.println("Connected to StudentsDB!");

            Statement statement =
                    connection.createStatement();

            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS students (
                        id INT PRIMARY KEY,
                        firstname VARCHAR(255),
                        lastname VARCHAR(255),
                        grade INT
                    )
                    """;

            statement.executeUpdate(createTableSQL);

            System.out.println("Students table ready!");

            statement.executeUpdate("DELETE FROM students");

            System.out.println("Existing student data cleared!");

            String insertSQL = """
                    INSERT INTO students
                    (id, firstname, lastname, grade)
                    VALUES
                    (1, 'Abebe', 'Kebede', 90),
                    (2, 'Hana', 'Tesfaye', 85),
                    (3, 'Dawit', 'Getachew', 78),
                    (4, 'Selam', 'Mekonnen', 92),
                    (5, 'Yonas', 'Alemu', 88),
                    (6, 'Liya', 'Haile', 95),
                    (7, 'Nahom', 'Berhanu', 76),
                    (8, 'Bethel', 'Tadesse', 89),
                    (9, 'Samuel', 'Bekele', 82),
                    (10, 'Mimi', 'Worku', 91),
                    (11, 'Eyob', 'Girma', 87)
                    """;

            int insertedRows =
                    statement.executeUpdate(insertSQL);

            System.out.println(
                    insertedRows + " students inserted successfully!"
            );

            System.out.println("\nFirst five students:");

            String selectSQL =
                    "SELECT * FROM students LIMIT 5";

            ResultSet resultSet =
                    statement.executeQuery(selectSQL);

            while (resultSet.next()) {

                int id =
                        resultSet.getInt("id");

                String firstname =
                        resultSet.getString("firstname");

                String lastname =
                        resultSet.getString("lastname");

                int grade =
                        resultSet.getInt("grade");

                System.out.println(
                        "ID: " + id +
                                ", Name: " +
                                firstname + " " +
                                lastname +
                                ", Grade: " + grade
                );
            }

            String findStudentSQL =
                    "SELECT firstname FROM students WHERE id = 1";

            ResultSet oldStudent =
                    statement.executeQuery(findStudentSQL);

            String oldName = "";

            if (oldStudent.next()) {
                oldName =
                        oldStudent.getString("firstname");
            }

            String updateSQL =
                    "UPDATE students " +
                            "SET firstname = ? " +
                            "WHERE id = ?";

            PreparedStatement updateStatement =
                    connection.prepareStatement(updateSQL);

            updateStatement.setString(1, "Amanuel");
            updateStatement.setInt(2, 1);

            int updatedRows =
                    updateStatement.executeUpdate();

            if (updatedRows > 0) {
                System.out.println(
                        "\nStudent updated successfully!"
                );
                System.out.println("ID: 1");
                System.out.println(
                        "Old first name: " + oldName
                );
                System.out.println(
                        "New first name: Amanuel"
                );
            }

            String findDeleteStudentSQL =
                    "SELECT firstname, lastname FROM students WHERE id = 11";

            ResultSet studentToDelete =
                    statement.executeQuery(findDeleteStudentSQL);

            String deletedName = "";
            String deletedLastName = "";

            if (studentToDelete.next()) {
                deletedName =
                        studentToDelete.getString("firstname");

                deletedLastName =
                        studentToDelete.getString("lastname");
            }

            String deleteSQL =
                    "DELETE FROM students WHERE id = ?";

            PreparedStatement deleteStatement =
                    connection.prepareStatement(deleteSQL);

            deleteStatement.setInt(1, 11);

            int deletedRows =
                    deleteStatement.executeUpdate();

            if (deletedRows > 0) {
                System.out.println(
                        "\nStudent deleted successfully!"
                );
                System.out.println("ID: 11");
                System.out.println(
                        "Deleted student: " +
                                deletedName + " " +
                                deletedLastName
                );
            }

            String averageSQL =
                    "SELECT AVG(grade) AS average_grade FROM students";

            ResultSet averageResult =
                    statement.executeQuery(averageSQL);

            if (averageResult.next()) {

                double average =
                        averageResult.getDouble("average_grade");

                System.out.println(
                        "\nAverage grade: " + average
                );
            }

            System.out.println("\nFinal student records:");

            ResultSet finalResult =
                    statement.executeQuery(
                            "SELECT * FROM students"
                    );

            while (finalResult.next()) {

                System.out.println(
                        "ID: " +
                                finalResult.getInt("id") +
                                ", Name: " +
                                finalResult.getString("firstname") +
                                " " +
                                finalResult.getString("lastname") +
                                ", Grade: " +
                                finalResult.getInt("grade")
                );
            }

            connection.close();

            System.out.println("\nConnection closed.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
