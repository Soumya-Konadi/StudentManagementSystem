
package main;

import java.util.List;
import java.util.Scanner;

import dao.StudentDAO;
import model.Student;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter age: ");
                    int age;

                    try {
                        age = Integer.parseInt(sc.nextLine().trim());
                        if (age <= 0) {
                            System.out.println("Age must be positive.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid age.");
                        break;
                    }

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter course: ");
                    String course = sc.nextLine();

                    Student newStudent =
                        new Student(name, age, email, course);

                    if (dao.addStudent(newStudent)) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Could not add student.");
                    }
                    break;

                case 2:
                    List<Student> students = dao.getAllStudents();

                    System.out.println("\n----- STUDENT LIST -----");

                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        for (Student s : students) {
                            System.out.println(
                                s.getId() + " | " +
                                s.getName() + " | " +
                                s.getAge() + " | " +
                                s.getEmail() + " | " +
                                s.getCourse()
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    try {
                        int searchId =
                            Integer.parseInt(sc.nextLine().trim());

                        Student found = dao.getStudentById(searchId);

                        if (found != null) {
                            System.out.println(
                                found.getId() + " | " +
                                found.getName() + " | " +
                                found.getAge() + " | " +
                                found.getEmail() + " | " +
                                found.getCourse()
                            );
                        } else {
                            System.out.println("Student not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid student ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID to update: ");

                    try {
                        int updateId =
                            Integer.parseInt(sc.nextLine().trim());

                        System.out.print("Enter new name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new age: ");
                        int newAge =
                            Integer.parseInt(sc.nextLine().trim());

                        if (newAge <= 0) {
                            System.out.println("Age must be positive.");
                            break;
                        }

                        System.out.print("Enter new email: ");
                        String newEmail = sc.nextLine();

                        System.out.print("Enter new course: ");
                        String newCourse = sc.nextLine();

                        Student updatedStudent = new Student(
                            updateId, newName, newAge,
                            newEmail, newCourse
                        );

                        if (dao.updateStudent(updatedStudent)) {
                            System.out.println("Student updated successfully!");
                        } else {
                            System.out.println("Student not found or update failed.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID or age.");
                    }
                    break;

                case 5:
                    System.out.print("Enter student ID to delete: ");

                    try {
                        int deleteId =
                            Integer.parseInt(sc.nextLine().trim());

                        System.out.print(
                            "Are you sure you want to delete this student? (yes/no): "
                        );

                        String confirmation = sc.nextLine();

                        if (confirmation.equalsIgnoreCase("yes")) {
                            if (dao.deleteStudent(deleteId)) {
                                System.out.println("Student deleted successfully!");
                            } else {
                                System.out.println("Student not found.");
                            }
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid student ID.");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:
                    System.out.println("Choose an option from 1 to 6.");
            }
        }
    }
}
