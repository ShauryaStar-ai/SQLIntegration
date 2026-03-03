package com.example.shaurya.Service;

import com.example.shaurya.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class StudentService {

    private final DataSource dataSource;

    @Autowired
    public StudentService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean makeStudent(Student student) {
        String studentName = student.getName();
        String studentEmail = student.getEmailAddress();

        String sql = "INSERT INTO Students (name, emailAddress) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, studentName);
            statement.setString(2, studentEmail);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean getAllStudents() {
        String sql = "SELECT * FROM Students";

        try{
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
