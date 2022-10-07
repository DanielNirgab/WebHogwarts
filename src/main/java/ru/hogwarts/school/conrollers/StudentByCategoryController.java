package ru.hogwarts.school.conrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentByCategoryService;

import java.util.List;

@RestController
@RequestMapping("byCategory")
public class StudentByCategoryController {

    private final StudentByCategoryService byCategoryService;

    public StudentByCategoryController(StudentByCategoryService byCategoryService) {
        this.byCategoryService = byCategoryService;
    }

    @GetMapping("/count")
    public Integer getCountOfStudents() {
        return byCategoryService.getCountOfStudents();
    }

    @GetMapping("/avgage")
    public Integer getAverageStudentsAge() {
        return byCategoryService.getAverageStudentAge();
    }

    @GetMapping("/showlast")
    public List<Student> showLastStudents () {
        return byCategoryService.showLastStudents();
    }
}
