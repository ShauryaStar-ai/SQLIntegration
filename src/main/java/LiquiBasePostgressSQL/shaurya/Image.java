package LiquiBasePostgressSQL.shaurya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class Image  {

    @Autowired
    DataSource dataBase;


    public void run(String... args) throws Exception {
        System.out.println("--- Running Image Uploader ---");
        // readFile(); // Commented out to prevent inserting on every run
        getImage(1); // Example: Get image with ID 1
        System.out.println("--- Image Uploader Finished ---");
    }

    public void readFile() {
        String imagePath = "C:/Users/sonuh/code/shaurya/java/LiquiBasePostgressSQL/Images/sunset.png";
        String sql = "INSERT INTO Images (name, data) VALUES (?, ?)";

        try (Connection connection = dataBase.getConnection();
             PreparedStatement sqlStatement = connection.prepareStatement(sql)) {

            try (FileInputStream fileInputStream = new FileInputStream(imagePath)) {
                byte[] imageData = new byte[fileInputStream.available()];
                fileInputStream.read(imageData);
                sqlStatement.setString(1, "SunSet");
                sqlStatement.setBytes(2, imageData);
            }

            int rowsAffected = sqlStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Success! Image data sent to database. Rows affected: " + rowsAffected);
            } else {
                System.err.println("Warning: The INSERT statement executed but did not affect any rows.");
            }

        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error: The file was not found at path: " + imagePath);
        } catch (Exception e) {
            System.err.println("An error occurred during the database operation.");
            e.printStackTrace();
        }
    }

    public void getImage(int id) {
        String sql = "SELECT * FROM Images WHERE id = ?";
        String imagePath = "C:/Users/sonuh/code/shaurya/java/LiquiBasePostgressSQL/Images/sunset.png";


        try (Connection connection = dataBase.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    byte[] data = resultSet.getBytes("data");
                    OutputStream outputStream = new FileOutputStream(imagePath);
                    outputStream.write(data);
                    outputStream.close();
                    System.out.println("Found image: " + name + " (Size: " + (data != null ? data.length : 0) + " bytes)");
                } else {
                    System.out.println("No image found with id: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
