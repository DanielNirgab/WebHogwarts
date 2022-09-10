package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.*;
import java.util.stream.Collectors;

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
        return studentRepositories.save(student);
    }

    public void deleteStudent(long id) {
        studentRepositories.deleteById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepositories.findAll();
    }

    public List<Student> rangeStudentsByAge(int age) {
        List<Student> rangedStudentListByAge = getAllStudents();
        return rangedStudentListByAge.stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
