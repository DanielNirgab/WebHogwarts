package ru.hogwarts.school.conrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.SortService;

import java.util.Collection;

@RestController
@RequestMapping("sort")
public class SortController {

    private final SortService sortService;
    private final FacultyService facultyService;

    public SortController(SortService studentService, FacultyService facultyService) {
        this.sortService = studentService;
        this.facultyService = facultyService;
    }


    @GetMapping("/by-first-letter")
    public ResponseEntity<Collection<String>> getSortedStudentsByFirstLetter(@RequestParam String letter) {
        return ResponseEntity.ok(sortService.sortStudentsByFirstLetter(letter));
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getStudentAverageAge() {
       return ResponseEntity.ok(sortService.sortStudentByAverageAge());
    }

    @GetMapping("/max-length")
    public ResponseEntity<String> getFacultyNameMaxLength() {
        return ResponseEntity.ok(facultyService.getFacultyNameMaxLength());
    }

    @GetMapping("/getSum")
    public ResponseEntity<Integer> getSum() {

        return ResponseEntity.ok(sortService.getSum());
    }
}
