
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Student;
import util.DBConnection;

public class StudentDAO {

    // Add a new student
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students (name, age, email, course) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getCourse());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // Display all students
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("email"),
                    rs.getString("course")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: "
                               + e.getMessage());
        }

        return students;
    }
    
    

    // Update an existing student's details
    public boolean updateStudent(Student student) {

        String sql = "UPDATE students SET name = ?, age = ?, "
                   + "email = ?, course = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getCourse());
            ps.setInt(5, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating student: "
                               + e.getMessage());
            return false;
        }
    }

    

    // Delete a student using their ID
    public boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting student: "
                               + e.getMessage());
            return false;
        }
    }

    

    // Find a student by ID
    public Student getStudentById(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getString("course")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching student: "
                               + e.getMessage());
        }

        return null;
    }

}
