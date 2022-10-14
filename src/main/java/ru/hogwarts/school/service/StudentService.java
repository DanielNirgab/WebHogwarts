package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.*;


@Service
public class StudentService {

    private final StudentRepositories studentRepositories;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepositories studentRepositories) {
        this.studentRepositories = studentRepositories;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepositories.save(student);
    }

    public Student getStudent(Long id) {
        logger.debug("Requesting id");
        logger.info("Was invoked method for getting student");
        return studentRepositories.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.debug("Requesting student");
        logger.info("Was invoked method for update student");
        Student studentToUpdate = studentRepositories.getReferenceById(student.getId());
        studentToUpdate.setName(student.getName());
        studentToUpdate.setAge(student.getAge());
        return studentRepositories.save(studentToUpdate);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        studentRepositories.deleteById(id);
    }

    public List<Student> getAllStudents() {
        logger.info("Was invoked method for getting all students");
        return studentRepositories.findAll();
    }


    public Student findByName (String name) {
        logger.debug("Requesting name");
        logger.info("Was invoked method for searching student by name");
        return studentRepositories.findByNameContains(name);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for searching student by age");
        return studentRepositories.findStudentByAge(age);
    }

    public Collection<Student> findByNamePart(String part) {
        logger.info("Was invoked method for searching student by part name");
        return studentRepositories.findAllByNameContainsIgnoreCase(part);
    }

    public Collection<Student> findStudentsAgesBetween(int ageStart, int ageEnd) {
        logger.info("Was invoked method for searching student by age between" + ageStart +
                " " + ageEnd);
        return studentRepositories.findByAgeBetween(ageStart, ageEnd);
    }
}
