package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;

import java.util.Collection;
import java.util.List;

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
        return facultyRepositories.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty facultyToUpdate = facultyRepositories.findFacultyById(faculty.getId());
        facultyToUpdate.setName(faculty.getName());
        facultyToUpdate.setColor(faculty.getColor());
        return facultyRepositories.save(facultyToUpdate);
    }

    public Faculty deleteFaculty(Long id) {
        Faculty foundedFaculty = facultyRepositories.findFacultyById(id);
       facultyRepositories.deleteById(id);
       return foundedFaculty;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepositories.findAll();
    }

    public Collection<Faculty> rangeFacultiesByColor(String color) {
        return facultyRepositories.findAllByColor(color);
    }

    public Collection<Faculty> getFacultiesByNameOrColor(String name, String color) {
        return facultyRepositories.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }


}
