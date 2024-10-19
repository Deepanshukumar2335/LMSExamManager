package com.crio.LMSExamManager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crio.LMSExamManager.dto.StudentDTO;
import com.crio.LMSExamManager.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void testGetAllStudents() throws Exception {
        // Arrange
        StudentDTO student1 = new StudentDTO();
        student1.setStudentId(1L);
        student1.setStudentName("John Doe");
        student1.setEnrolledSubjectIds(Arrays.asList(101L));

        StudentDTO student2 = new StudentDTO();
        student2.setStudentId(2L);
        student2.setStudentName("Jane Doe");
        student2.setEnrolledSubjectIds(Arrays.asList(102L));
        List<StudentDTO> studentList = Arrays.asList(student1, student2);


        when(studentService.getAllStudents()).thenReturn(studentList);

        // Act & Assert
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].studentName").value("John Doe"))
                .andExpect(jsonPath("$[1].studentId").value(2))
                .andExpect(jsonPath("$[1].studentName").value("Jane Doe"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById_Found() throws Exception {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(1L);
        studentDTO.setStudentName("John Doe");
        studentDTO.setEnrolledSubjectIds(Arrays.asList(101L));
        when(studentService.getStudentById(1L)).thenReturn(studentDTO);

        // Act & Assert
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.studentName").value("John Doe"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void testCreateStudent() throws Exception {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentName("John Doe");

        StudentDTO savedStudentDTO = new StudentDTO();
        savedStudentDTO.setStudentId(1L);
        savedStudentDTO.setStudentName("John Doe");

        when(studentService.saveStudent(any(StudentDTO.class))).thenReturn(savedStudentDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(studentDTO);

        // Act & Assert
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.studentName").value("John Doe"));

        verify(studentService, times(1)).saveStudent(any(StudentDTO.class));
    }

    @Test
    void testEnrollStudentInSubjects() throws Exception {
        // Arrange
        Long studentId = 1L;
        List<Long> subjectIds = Arrays.asList(101L, 102L);

        doNothing().when(studentService).enrollStudentInSubjects(studentId, subjectIds);

        ObjectMapper objectMapper = new ObjectMapper();
        String subjectIdsJson = objectMapper.writeValueAsString(subjectIds);

        // Act & Assert
        mockMvc.perform(post("/students/1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(subjectIdsJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Student enrolled in subjects successfully."));

        verify(studentService, times(1)).enrollStudentInSubjects(studentId, subjectIds);
    }

    @Test
    void testDeleteStudent() throws Exception {
        // Arrange
        Long studentId = 1L;

        doNothing().when(studentService).deleteStudent(studentId);

        // Act & Assert
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudent(studentId);
    }
}
