package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepositories facultyRepositories;


    public FacultyService(FacultyRepositories facultyRepositories) {
        this.facultyRepositories = facultyRepositories;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepositories.save(faculty);
    }

    public Faculty getFaculty(long id) {
        return facultyRepositories.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepositories.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepositories.deleteById(id);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepositories.findAll();
    }

        public List<Faculty> rangeFacultiesByColor(String color) {
          return   getAllFaculties().stream()
                    .filter(faculty -> faculty.getColor().equals(color))
                    .collect(Collectors.toList());
        }


}
