package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepositories facultyRepositories;


    public FacultyService(FacultyRepositories facultyRepositories) {
        this.facultyRepositories = facultyRepositories;
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Requesting faculty:{}", faculty);
        logger.info("Was invoked method for creat faculty");
        return facultyRepositories.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("Was invoked method for get faculty");
        return facultyRepositories.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Requesting faculty:{}", faculty);
        logger.info("Was invoked method for edit faculty");
        Faculty facultyToUpdate = facultyRepositories.findFacultyById(faculty.getId());
        facultyToUpdate.setName(faculty.getName());
        facultyToUpdate.setColor(faculty.getColor());
        return facultyRepositories.save(facultyToUpdate);
    }

    public Faculty deleteFaculty(Long id) {
        logger.debug("Requesting id:{}", id);
        logger.info("Was invoked method for delete faculty");
        Faculty foundedFaculty = facultyRepositories.findFacultyById(id);
       facultyRepositories.deleteById(id);
       return foundedFaculty;
    }

    public List<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepositories.findAll();
    }

    public Collection<Faculty> rangeFacultiesByColor(String color) {
        logger.info("Was invoked method for range faculties by color");
        return facultyRepositories.findAllByColor(color);
    }

    public Collection<Faculty> getFacultiesByNameOrColor(String name, String color) {
        logger.info("Was invoked method for get faculties by name or color");
        return facultyRepositories.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public String getFacultyNameMaxLength () {
        return facultyRepositories.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElse("Список пуст");

    }
}
