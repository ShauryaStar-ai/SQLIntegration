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
        double balance = student.getBalance();

        String sql = "INSERT INTO Students (name, emailAddress, balance) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id", "bankaccnum"})) {

            statement.setString(1, studentName);
            statement.setString(2, studentEmail);
            statement.setDouble(3,balance);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setId(generatedKeys.getInt("id"));
                        student.setBankAccNum(generatedKeys.getInt("bankaccnum"));
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
                student.setEmailAddress(resultSet.getString("emailaddress"));
                student.setBankAccNum(resultSet.getInt("bankaccnum"));
                student.setBalance(resultSet.getDouble("balance"));
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
    public boolean sendMoneryFromOneAccToOther(int fromId, int toId, double amount) {
        String sqlSelect = "SELECT * FROM Students WHERE id = ?";
        String sqlUpdate = "UPDATE Students SET balance = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
                 PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate)) {

                // Get 'from' student
                selectStmt.setInt(1, fromId);
                ResultSet rsFrom = selectStmt.executeQuery();
                Student fromStudent = null;
                if (rsFrom.next()) {
                    fromStudent = new Student();
                    fromStudent.setId(rsFrom.getInt("id"));
                    fromStudent.setBalance(rsFrom.getDouble("balance"));
                }

                // Get 'to' student
                selectStmt.setInt(1, toId);
                ResultSet rsTo = selectStmt.executeQuery();
                Student toStudent = null;
                if (rsTo.next()) {
                    toStudent = new Student();
                    toStudent.setId(rsTo.getInt("id"));
                    toStudent.setBalance(rsTo.getDouble("balance"));
                }

                if (fromStudent != null && toStudent != null && fromStudent.getBalance() >= amount) {
                    // Debit from 'from' student
                    updateStmt.setDouble(1, fromStudent.getBalance() - amount);
                    updateStmt.setInt(2, fromStudent.getId());
                    int rowsaffected = updateStmt.executeUpdate();

                    // Credit to 'to' student
                    updateStmt.setDouble(1, toStudent.getBalance() + amount);
                    updateStmt.setInt(2, toStudent.getId());
                    int rowsaffected2 = updateStmt.executeUpdate();
                    if(rowsaffected > 0 && rowsaffected2 > 0){
                    connection.commit();
                    return true;}
                    else{
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean editStudentById(Student student){
        String emailAddress = student.getEmailAddress();
        String name = student.getName();
        int id = student.getId();

        String sql = "UPDATE Students SET name = ?, emailaddress = ? WHERE id = ?";
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
