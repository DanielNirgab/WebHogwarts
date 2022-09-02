package ru.hogwarts.school.conrollers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping
    public Faculty editFaculty(Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping
    public Faculty deleteFaculty(long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping({"/range-faculty/{color}"})
    public Collection<Faculty> rangeFacultiesByColor(@PathVariable String color) {
        return facultyService.rangeFacultiesByColor(color);
    }

}
