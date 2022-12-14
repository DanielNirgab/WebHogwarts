package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepositories;
import ru.hogwarts.school.repositories.StudentRepositories;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarDis;

    private final StudentRepositories studentRepositories;
    private final AvatarRepositories avatarRepositories;

    public AvatarService(StudentRepositories studentRepositories, AvatarRepositories avatarRepositories) {
        this.studentRepositories = studentRepositories;
        this.avatarRepositories = avatarRepositories;
    }

    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.error("There is some Error");
        logger.info("Was invoked method for upload avatar");

        Student student = studentRepositories.getReferenceById(studentId);

        Path filePath = Path.of(avatarDis, student + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepositories.save(avatar);
    }
    private String getExtensions(String fileName) {
        logger.debug("Requesting file name:{}", fileName);
        logger.info("Was invoked method for get extensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method find avatar");
        return avatarRepositories.findByStudentId(id).orElse(new Avatar());
    }

    public Collection<Avatar> findAll(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method find all avatars");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return avatarRepositories.findAll(pageRequest).getContent();
    }
}
