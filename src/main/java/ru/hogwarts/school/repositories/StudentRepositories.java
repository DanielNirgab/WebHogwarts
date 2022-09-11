package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepositories extends JpaRepository <Student, Long> {
    Student findByNameContains(String name);

    Collection<Student> findStudentByAge(int age);

    Collection<Student> findAllByNameContainsIgnoreCase(String part);

}
