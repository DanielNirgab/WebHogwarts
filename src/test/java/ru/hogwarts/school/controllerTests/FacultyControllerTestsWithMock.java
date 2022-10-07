package ru.hogwarts.school.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.conrollers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepositories;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTestsWithMock {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepositories facultyRepositories;
    @SpyBean
    private FacultyService facultyService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_createFaculty() throws Exception {
        Long id = 1L;
        String name = "facultyTest1";
        String color = "testColor";

        JSONObject facultyObj = new JSONObject();
        facultyObj.put("name", name);
        facultyObj.put("color", color);

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepositories.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepositories.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void test_updateFaculty() throws Exception {
        Long id = 1L;
        String name = "facultyTest1";
        String color = "testColor";

        String name2 = "facultyTest2";
        String color2 = "testColor";

        JSONObject facultyObj = new JSONObject();
        facultyObj.put("id", id);
        facultyObj.put("name", name2);
        facultyObj.put("color", color2);

        Faculty faculty = new Faculty(id, name, color);
        Faculty updatedFaculty = new Faculty(id, name2, color2);

        when(facultyRepositories.findFacultyById(id)).thenReturn(faculty);
        when(facultyRepositories.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name2))
                .andExpect(jsonPath("$.color").value(color2));
    }

    @Test
    public void test_deleteFaculty() throws Exception {
        Long id = 1L;
        String name = "facultyTest1";
        String color = "testColor";

        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepositories.findFacultyById(id)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepositories, atLeastOnce()).deleteById(id);
    }

    @Test
    public void test_getFaculty() throws Exception {
        Long id = 1L;
        String name = "facultyTest1";
        String color = "testColor";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepositories.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }


    @Test
    public void test_findFacultyByColor() throws Exception {
        Long id = 1L;
        String name1 = "facultyTest1";

        Long id2 = 2L;
        String name2 = "facultyTest2";

        String color = "Green";

        Faculty faculty1 = new Faculty(id, name1, color);
        Faculty faculty2 = new Faculty(id2, name2, color);

        when(facultyRepositories.findAllByColor(color)).thenReturn(Set.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/range-faculty/{color}", color)
                        .queryParam("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty1, faculty2))));
    }

    @Test
    public void test_getFacultyByNameOrColor() throws Exception {
        Long id = 1L;
        String name1 = "facultyTest1";
        String color1 = "Yellow";
        String color1IgnoreCase = "YeLLoW";

        Long id2 = 2L;
        String name2 = "facultyTest2";
        String color2 = "Brown";
        String name2IgnoreCase = "FACULTYTest2";

        Faculty faculty1 = new Faculty(id, name1, color1);
        Faculty faculty2 = new Faculty(id2, name2, color2);

        when(facultyRepositories.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name2IgnoreCase, name2IgnoreCase))
                .thenReturn(Set.of(faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findFaculty")
                        .queryParam("nameOrColor", name2IgnoreCase)
//                        .queryParam("color", color2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(faculty2))));

    }


}
