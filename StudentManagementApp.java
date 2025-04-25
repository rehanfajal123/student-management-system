
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentManagementApp extends JFrame {
    JTextField tfName, tfRoll, tfCourse, tfMarks;
    JButton btnAdd, btnUpdate, btnDelete, btnView;

    public StudentManagementApp() {
        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);

        add(new JLabel("Roll No:"));
        tfRoll = new JTextField();
        add(tfRoll);

        add(new JLabel("Course:"));
        tfCourse = new JTextField();
        add(tfCourse);

        add(new JLabel("Marks:"));
        tfMarks = new JTextField();
        add(tfMarks);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnView = new JButton("View");

        add(btnAdd);
        add(btnUpdate);
        add(btnDelete);
        add(btnView);

        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnView.addActionListener(e -> viewStudents());

        setVisible(true);
    }

    private void addStudent() {
        try (Connection con = StudentDB.getConnection()) {
            String sql = "INSERT INTO students(name, roll_no, course, marks) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfName.getText());
            ps.setString(2, tfRoll.getText());
            ps.setString(3, tfCourse.getText());
            ps.setInt(4, Integer.parseInt(tfMarks.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateStudent() {
        try (Connection con = StudentDB.getConnection()) {
            String sql = "UPDATE students SET name=?, course=?, marks=? WHERE roll_no=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfName.getText());
            ps.setString(2, tfCourse.getText());
            ps.setInt(3, Integer.parseInt(tfMarks.getText()));
            ps.setString(4, tfRoll.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student updated successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteStudent() {
        try (Connection con = StudentDB.getConnection()) {
            String sql = "DELETE FROM students WHERE roll_no=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tfRoll.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewStudents() {
        try (Connection con = StudentDB.getConnection()) {
            String sql = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            StringBuilder sb = new StringBuilder("Students List:\n");
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Name: ").append(rs.getString("name"))
                  .append(", Roll No: ").append(rs.getString("roll_no"))
                  .append(", Course: ").append(rs.getString("course"))
                  .append(", Marks: ").append(rs.getInt("marks")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentManagementApp();
    }
}
