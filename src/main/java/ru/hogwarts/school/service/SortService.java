package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepositories;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SortService {
    private final StudentRepositories studentRepositories;

    public SortService(StudentRepositories studentRepositories) {
        this.studentRepositories = studentRepositories;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Collection<String> sortStudentsByFirstLetter(String letter) {
        logger.debug("Requesting first letter");
        logger.info("Was invoked method for getting sorted students");
        return studentRepositories.findAll().stream()
                .map(Student::getName)
                .filter(s -> s.startsWith(letter))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double sortStudentByAverageAge () {
        logger.info("Was invoked method for getting Students average age");
        return studentRepositories.findAll().stream()
                .mapToDouble(Student::getAge)
                .average().orElseThrow();
    }

    public Integer getSum() {

        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        return sum;
    }

}
