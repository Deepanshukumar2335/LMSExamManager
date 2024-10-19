package com.crio.LMSExamManager.controller;

import com.crio.LMSExamManager.dto.examDto;
import com.crio.LMSExamManager.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Examcontroller.class)
public class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllExams() throws Exception {
        // Create dummy examDto data
        examDto exam1 = new examDto();
        exam1.setExamId(1L);
        exam1.setExamName("Math Exam");
        exam1.setSubjectId(1L);

        examDto exam2 = new examDto();
        exam2.setExamId(2L);
        exam2.setExamName("Science Exam");
        exam2.setSubjectId(2L);

        List<examDto> examList = Arrays.asList(exam1, exam2);

        // Mocking the service call
        when(examService.getAllExams()).thenReturn(examList);

        // Perform the GET request
        mockMvc.perform(get("/exams")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(examList.size()))
                .andExpect(jsonPath("$[0].examName").value("Math Exam"))
                .andExpect(jsonPath("$[1].examName").value("Science Exam"));

        // Verify the interaction with the service
        verify(examService, times(1)).getAllExams();
    }

    @Test
    public void testGetExamById_Success() throws Exception {
        examDto exam = new examDto();
        exam.setExamId(1L);
        exam.setExamName("Math Exam");
        exam.setSubjectId(1L);

        // Mocking the service call
        when(examService.getExamById(1L)).thenReturn(exam);

        // Perform the GET request
        mockMvc.perform(get("/exams/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examId").value(1L))
                .andExpect(jsonPath("$.examName").value("Math Exam"));

        // Verify the interaction with the service
        verify(examService, times(1)).getExamById(1L);
    }

    @Test
    public void testGetExamById_NotFound() throws Exception {
        // Mocking the service call
        when(examService.getExamById(1L)).thenThrow(new RuntimeException("Exam not found"));

        // Perform the GET request
        mockMvc.perform(get("/exams/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Verify the interaction with the service
        verify(examService, times(1)).getExamById(1L);
    }

    @Test
    public void testCreateExam_Success() throws Exception {
        examDto exam = new examDto();
        exam.setExamId(1L);
        exam.setExamName("Math Exam");
        exam.setSubjectId(1L);

        List<examDto> examList = Arrays.asList(exam);

        // Mocking the service call
        when(examService.saveExams(anyList())).thenReturn(examList);

        // Perform the POST request
        mockMvc.perform(post("/exams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(examList)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].examId").value(1L))
                .andExpect(jsonPath("$[0].examName").value("Math Exam"));

        // Verify the interaction with the service
        verify(examService, times(1)).saveExams(anyList());
    }

    @Test
    public void testRegisterStudentForExam_Success() throws Exception {
        Long studentId = 1L;
        Long examId = 1L;

        // Perform the POST request
        mockMvc.perform(post("/exams/register/{studentId}/{examId}", studentId, examId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Student registered for the exam successfully"));

        // Verify the interaction with the service
        verify(examService, times(1)).registerStudentForExam(studentId, examId);
    }

    @Test
    public void testDeleteExam_Success() throws Exception {
        Long examId = 1L;

        // Perform the DELETE request
        mockMvc.perform(delete("/exams/{id}", examId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the interaction with the service
        verify(examService, times(1)).deleteExam(examId);
    }
}
