package LiquiBasePostgressSQL.shaurya.Service;

import LiquiBasePostgressSQL.shaurya.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM Students";
        List<Student> students = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setEmailAddress(resultSet.getString("emailAddress"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!students.isEmpty()){
        return students;}
        else{
            return null;
        }
        
    }
    public boolean editStudentById(Student student){
        String emailAddress = student.getEmailAddress();
        String name = student.getName();
        int id = student.getId();

        String sql = "UPDATE Students SET name = ?, emailAddress = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,name);
            statement.setString(2,emailAddress);
            statement.setInt(3,id);
            int numRowsUpdated = statement.executeUpdate();

            if(numRowsUpdated>0){
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Student getStudentByID(int id ){
        List<Student> allStundets = getAllStudents();
        if(allStundets == null){return null;}
        for(Student student : allStundets){if(student.getId() == id){return student;}}
        {return null;}
    }
    public boolean deleteStudentByID(int id){
        Student student = getStudentByID(id);
        if(student != null){
            String sql = "DELETE FROM Students WHERE id = ?";
            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1,id);

                // now how to get conformation that the statement acutlly ran ?
                // and then based upon that return the boolean result.

                int numRowsDeleted = statement.executeUpdate();
                if(numRowsDeleted > 0){
                    return true;
                }
            }


            catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return false;
    }

    public List<Map<String, Object>> getStudentJoinBook() {
        String sql = "SELECT s.name, b.name FROM Students s JOIN Books b ON s.id = b.ownerId";
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("studentName", resultSet.getString(1));
                row.put("bookName", resultSet.getString(2));
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
