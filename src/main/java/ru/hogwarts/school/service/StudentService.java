package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        return students.put(lastId, student);
    }

    public Student getStudent(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    public List<Student> rangeStudentsByAge(int age) {
        List<Student> rangedStudentListByAge = getAllStudents();
        return rangedStudentListByAge.stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
}
