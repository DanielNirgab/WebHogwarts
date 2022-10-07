package ru.hogwarts.school.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Student;

import java.net.URI;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {
    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = createStudent("TestStudent", 25);
        ResponseEntity<Student> response = whenStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(response);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = createStudent("TestStudent", 25);
        ResponseEntity<Student> createdPerson = whenStudentRequest(getUriBuilder().build().toUri(), student);
        thenStudentHasBeenCreated(createdPerson);

        Student createdStudent = createdPerson.getBody();
        thenStudentIdFound(createdStudent.getId(), createdStudent);
    }

    @Test
    public void testUpdateStudent() {
        Student firstStudent = createStudent("TestStudent", 25);

        ResponseEntity<Student> responseEntity = whenStudentRequest(getUriBuilder().build().toUri(), firstStudent);
        thenStudentHasBeenCreated(responseEntity);
        Student secondStudent = responseEntity.getBody();

        whenStudentUpdate(secondStudent, 66, "Dambldor");
        thenStudentHasBennUpdated(secondStudent, 66, "Dambldor");

    }

    @Test
    public void testDelete() {
        Student firstStudent = createStudent("TestStudent", 25);

        ResponseEntity<Student> responseEntity = whenStudentRequest(getUriBuilder().build().toUri(), firstStudent);
        thenStudentHasBeenCreated(responseEntity);
        Student createdStudent = responseEntity.getBody();

        restTemplate.delete(getUriBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri());

        URI getUri = getUriBuilder().path("/{id}").buildAndExpand(createdStudent.getId()).toUri();
        ResponseEntity<Student> emptyRs = restTemplate.getForEntity(getUri, Student.class);

        assertThat(emptyRs.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testFindByAge() {
        Student student10 = createStudent("Student10", 10);
        Student student11 = createStudent("Student11", 11);
        Student student12 = createStudent("Student12", 12);
        Student student13 = createStudent("Student13", 13);

        whenStudentRequest(getUriBuilder().build().toUri(), student10);
        whenStudentRequest(getUriBuilder().build().toUri(), student11);
        whenStudentRequest(getUriBuilder().build().toUri(), student12);
        whenStudentRequest(getUriBuilder().build().toUri(), student13);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "12");
        thenStudentsFoundCriteria(queryParams, student12);
    }

    @Test
    public void testFindByAgeBetween() {
        Student student10 = createStudent("Student10", 10);
        Student student11 = createStudent("Student11", 11);
        Student student12 = createStudent("Student12", 12);
        Student student13 = createStudent("Student13", 13);

        whenStudentRequest(getUriBuilder().build().toUri(), student10);
        whenStudentRequest(getUriBuilder().build().toUri(), student11);
        whenStudentRequest(getUriBuilder().build().toUri(), student12);
        whenStudentRequest(getUriBuilder().build().toUri(), student13);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("age", "12");
        queryParams.add("age", "14");
        thenStudentsFoundCriteria(queryParams, student12, student13);
    }


    ////////////////////////////////////////////////////////////////
    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/student");
    }

    private void whenStudentUpdate(Student secondStudent, int newAge, String newName) {
        secondStudent.setAge(newAge);
        secondStudent.setName(newName);
        System.out.println(secondStudent.getId());
        restTemplate.put(getUriBuilder().build().toUri(), secondStudent);

    }

    private void thenStudentHasBennUpdated(Student secondStudent, int newAge, String newName) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(secondStudent.getId()).toUri();
        ResponseEntity<Student> updatedStudent = restTemplate.getForEntity(uri, Student.class);
        System.out.println(uri);
        assertThat(updatedStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedStudent.getBody()).isNotNull();
        assertThat(updatedStudent.getBody().getAge()).isEqualTo(newAge);
        assertThat(updatedStudent.getBody().getName()).isEqualTo(newName);

    }

    private Student createStudent(String name, int age) {
        return new Student(name, age);
    }

    private void thenStudentHasBeenCreated(ResponseEntity<Student> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isNotNull();
        assertThat(response.getBody().getAge()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    private ResponseEntity<Student> whenStudentRequest(URI uri, Student student) {
        return restTemplate.postForEntity(uri, student, Student.class);
    }

    private void thenStudentIdFound(Long studentId, Student student) {
        URI uri = getUriBuilder().path("/{id}").buildAndExpand(studentId).toUri();
        ResponseEntity<Student> response = restTemplate.getForEntity(uri, Student.class);
        assertThat(response.getBody()).isEqualTo(student);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void thenStudentsFoundCriteria(MultiValueMap<String, String> queryParams, Student... students) {
        URI uri = getUriBuilder().queryParams(queryParams).build().toUri();

        ResponseEntity<Set<Student>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Student>>() {
                });

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Set<Student> actualResult = response.getBody();
        resetIDs(actualResult);

        assertThat(actualResult.toArray()).contains(students);
    }

    private void resetIDs(Collection<Student> studentCollections) {
        studentCollections.forEach(it -> it.setId(null));
    }
}
