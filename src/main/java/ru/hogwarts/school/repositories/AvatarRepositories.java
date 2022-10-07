package ru.hogwarts.school.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepositories extends PagingAndSortingRepository<Avatar, Integer> {
    Optional<Avatar> findByStudentId (Long id);


}
