package ru.hogwarts.school.conrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student student1 = studentService.createStudent(student);
        return ResponseEntity.ok(student1);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent (@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(@RequestParam (required = false) String name,
                                                              @RequestParam (required = false) String partName) {
        if (name != null && name.isBlank()) {
            ResponseEntity.ok(studentService.findByName(name));
        }
        if (name != null && name.isBlank()) {
            ResponseEntity.ok(studentService.findByNamePart(partName));
        }

        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/rangestudents/{age}")
    public Collection<Student> getRangedStudentsByAge(@PathVariable int age) {
        return studentService.findStudentsByAge(age);
    }

    @GetMapping("/rangestudents/age_between")
    public Collection<Student> getStudentsAgeBetween(@RequestParam int ageStart, @RequestParam int ageEnd) {
        return studentService.findStudentsAgesBetween(ageStart, ageEnd);
    }

    @GetMapping("/{id}/faculty/")
    public ResponseEntity <Faculty> getStudentFaculty(@PathVariable long id) {
        return ResponseEntity.ok(studentService.getStudent(id).getFaculty());
    }

    @GetMapping("/threads")
    public void getNamesUsingStreams() {
        studentService.returnNamesUsingStreams();
    }

    @GetMapping("/namesPairs")
    public void getNamesPairs() {
        studentService.getNamesPairs();
    }

}