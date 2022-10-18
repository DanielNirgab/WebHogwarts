package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentsByCategoryRepository;

import java.util.List;

@Service
public class StudentByCategoryService {
    private StudentsByCategoryRepository categoryRepository;

    public StudentByCategoryService(StudentsByCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentByCategoryService.class);
    public Integer getCountOfStudents() {
        logger.info("Was invoked method for getting count of students");
        return categoryRepository.getCountOfStudents();
    }

    public Double getAverageStudentAge() {
        logger.info("Was invoked method for getting average students age");
        return categoryRepository.getAverageStudentAge();
    }

    public List<Student> showLastStudents() {
        logger.info("Was invoked method for showing last students");
        return categoryRepository.showLastStudentList();
    }
}
