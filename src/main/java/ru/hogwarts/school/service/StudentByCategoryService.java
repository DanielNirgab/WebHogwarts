package ru.hogwarts.school.service;

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

    public Integer getCountOfStudents() {
        return categoryRepository.getCountOfStudents();
    }

    public Double getAverageStudentAge() {
        return categoryRepository.getAverageStudentAge();
    }

    public List<Student> showLastStudents() {
        return categoryRepository.showLastStudentList();
    }
}
