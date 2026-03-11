package LiquiBasePostgressSQL.shaurya.Controller;

import LiquiBasePostgressSQL.shaurya.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import LiquiBasePostgressSQL.shaurya.Model.Book;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book){
        boolean success = bookService.addBook(book);
        if(success){
            return ResponseEntity.status(200).body("Book added successfully");
        }
        else{
            return ResponseEntity.status(500).body("Book could not be added");
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllBooks(){
        if(bookService.getAllBooks() == null){
            return ResponseEntity.status(500).body("Books could not be returned");
        }
        return ResponseEntity.status(200).body(bookService.getAllBooks());


    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book book){
        boolean success = bookService.updateBook(id, book);
        if(success){
            return ResponseEntity.status(200).body("Book updated successfully");
        }
        else{
            return ResponseEntity.status(500).body("Book could not be updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        boolean success = bookService.deleteBook(id);
        if(success){
            return ResponseEntity.status(200).body("Book deleted successfully");
        }
        else{
            return ResponseEntity.status(500).body("Book could not be deleted");
        }
    }

}
