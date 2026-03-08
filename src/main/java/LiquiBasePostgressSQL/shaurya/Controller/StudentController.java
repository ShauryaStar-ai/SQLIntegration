package LiquiBasePostgressSQL.shaurya.Controller;

import LiquiBasePostgressSQL.shaurya.Model.Student;
import LiquiBasePostgressSQL.shaurya.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        List<Student> allStudentsReturned = studentService.getAllStudents();
        if(allStudentsReturned != null){
            return ResponseEntity.status(200).body(allStudentsReturned);
        }
        else{
            return ResponseEntity.status(500).body("All students couldnot be returned");
        }
    }
    @PutMapping
    public ResponseEntity<?> editStudentInfoByStudentID(@RequestBody Student student){
        boolean b = studentService.editStudentById(student);
        if(b){
            int editedStudentId = student.getId();

            return ResponseEntity.status(200).body("Student edited successfully"+student);

        }
        else{
            return ResponseEntity.status(500).body("Student could not be edited");
        }

    }
    @GetMapping("/getStudentByID")
    public ResponseEntity<?> getStudentByID(@RequestParam int id){
        Student student = studentService.getStudentByID(id);
        if(student != null){
            return ResponseEntity.status(200).body("Student with ID " + student);
        }
        else{
            return ResponseEntity.status(500).body("Student could not be found");
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteStudentByID(@RequestParam int id){
        boolean isStudentDeleted = studentService.deleteStudentByID(id);
        if (isStudentDeleted){
            return ResponseEntity.status(200).body("Student with ID " + "deleted");
        }
        else{
            return ResponseEntity.status(500).body("Student could not be found");
        }
    }

    @GetMapping("/join-book")
    public ResponseEntity<?> getStudentJoinBook() {
        List<Map<String, Object>> result = studentService.getStudentJoinBook();
        if (!result.isEmpty()) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(500).body("Could not retrieve joined data");
        }
    }
}
