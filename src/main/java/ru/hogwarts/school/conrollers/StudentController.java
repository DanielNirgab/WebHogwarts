package ru.hogwarts.school.conrollers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable long id) {
        return studentService.getStudent(id);
    }

    @PutMapping
    public Student editStudent(Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping()
    public Student deleteStudent(long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/rangestudents/{age}")
    public Collection<Student> getRangedStudentsByAge(@PathVariable int age) {
        return studentService.rangeStudentsByAge(age);
    }

}
