package LiquiBasePostgressSQL.shaurya.Service;

import LiquiBasePostgressSQL.shaurya.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    DataSource dataSource;

    public boolean addBook(Book book) {
        String bookName = book.getName();
        int ownerId = book.getOwnerId();

        String sql = "INSERT INTO Books (name, ownerId) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlStatement = connection.prepareStatement(sql)) {

            sqlStatement.setString(1, bookName);
            sqlStatement.setInt(2, ownerId);

            int rowsAffected = sqlStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Connection connection = dataSource.getConnection(); PreparedStatement sqlStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = sqlStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setOwnerId(resultSet.getInt("ownerId"));
                books.add(book);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public boolean updateBook(int id, Book book) {
        String sql = "UPDATE Books SET name = ?, ownerId = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlStatement = connection.prepareStatement(sql)) {

            sqlStatement.setString(1, book.getName());
            sqlStatement.setInt(2, book.getOwnerId());
            sqlStatement.setInt(3, id);

            int rowsAffected = sqlStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBook(int id) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement sqlStatement = connection.prepareStatement(sql)) {

            sqlStatement.setInt(1, id);

            int rowsAffected = sqlStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
