package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepositories studentRepositories;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    Object flag = new Object();

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


    public Student findByName(String name) {
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

    //Шаг 1. Получаем имена студентов и выводим в консоль
    public void getStudentsForStreams(int index) {
        List<Student> students = studentRepositories.findAll();
        Student student = students.get(index);
        System.out.println(student.getName());
    }


    // Используем 3 потока для вывода имен
    public void returnNamesUsingStreams() {
        getStudentsForStreams(0);
        getStudentsForStreams(1);

        new Thread(() -> {
            getStudentsForStreams(2);
            getStudentsForStreams(3);
        }).start();

        new Thread(() -> {
            getStudentsForStreams(4);
            getStudentsForStreams(5);
        }).start();
    }


    // Шаг 2. Метод получения имён и вывода в консоль для потоков
    public synchronized void getNamesPairsForStreams(int count1, int count2) {
        synchronized (flag) {
            List<Student> students = studentRepositories.findAll();
            Student student1 = students.get(count1);
            Student student2 = students.get(count2);
            System.out.println(student1.getName());
            System.out.println(student2.getName());
        }
    }

    public void getNamesPairs() {
        getNamesPairsForStreams(0, 1);

        new Thread(() -> {
            getNamesPairsForStreams(2, 3);
        }).start();

        new Thread(() -> {
            getNamesPairsForStreams(4, 5);
        }).start();
    }




}
