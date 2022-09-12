package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepositories studentRepositories;

    public StudentService(StudentRepositories studentRepositories) {
        this.studentRepositories = studentRepositories;
    }


    public Student createStudent(Student student) {
        return studentRepositories.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepositories.findById(id).get();
    }

    public Student editStudent(Student student) {
        Student studentToUpdate = studentRepositories.getReferenceById(student.getId());
        studentToUpdate.setName(student.getName());
        studentToUpdate.setAge(student.getAge());
        return studentRepositories.save(studentToUpdate);
    }

    public void deleteStudent(long id) {
        studentRepositories.deleteById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepositories.findAll();
    }


    public Student findByName (String name) {
        return studentRepositories.findByNameContains(name);
    }

    public Collection<Student> findStudentsByAge(int age) {
        return studentRepositories.findStudentByAge(age);
    }

    public Collection<Student> findByNamePart(String part) {
        return studentRepositories.findAllByNameContainsIgnoreCase(part);
    }

    public Collection<Student> findStudentsAgesBetween(int ageStart, int ageEnd) {
        return studentRepositories.findByAgeBetween(ageStart, ageEnd);
    }


}
