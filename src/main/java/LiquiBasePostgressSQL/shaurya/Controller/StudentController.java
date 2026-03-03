package com.example.shaurya.Controller;

import com.example.shaurya.Model.Student;
import com.example.shaurya.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity<?> makeStudent(@RequestBody Student student){
        boolean success = studentService.makeStudent(student);
        if (success) {
            return ResponseEntity.status(200).body("Student created successfully");
        } else {
            return ResponseEntity.status(500).body("Student could not be made");
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllStudent(){
        boolean allStudentsReturned =   studentService.getAllStudents();
        if(allStudentsReturned){
            return ResponseEntity.status(200).body("All students returned");
        }
        else{
            return ResponseEntity.status(500).body("All students couldnot be returned");
        }
    }
}
