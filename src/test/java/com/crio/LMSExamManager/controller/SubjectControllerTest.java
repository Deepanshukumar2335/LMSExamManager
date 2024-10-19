package com.crio.LMSExamManager.controller;

import com.crio.LMSExamManager.dto.SubjectDTO;
import com.crio.LMSExamManager.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubjectControllerTest {

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    private SubjectDTO subjectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectId(1L);
        subjectDTO.setSubjectName("Mathematics");
        subjectDTO.setRegisteredStudentIds(Arrays.asList(1L, 2L));
    }

    @Test
    void testGetAllSubjects() {
        List<SubjectDTO> subjectList = Arrays.asList(subjectDTO);
        when(subjectService.getAllSubjects()).thenReturn(subjectList);

        ResponseEntity<List<SubjectDTO>> response = subjectController.getAllSubjects();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectList, response.getBody());
        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void testGetSubjectById() {
        when(subjectService.getSubjectById(1L)).thenReturn(subjectDTO);

        ResponseEntity<SubjectDTO> response = subjectController.getSubjectById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subjectDTO, response.getBody());
        verify(subjectService, times(1)).getSubjectById(1L);
    }

    @Test
    void testGetSubjectById_NotFound() {
        when(subjectService.getSubjectById(1L)).thenThrow(new RuntimeException("Subject not found"));

        ResponseEntity<SubjectDTO> response = subjectController.getSubjectById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(subjectService, times(1)).getSubjectById(1L);
    }

    @Test
    void testCreateSubject() {
        when(subjectService.saveSubject(any(SubjectDTO.class))).thenReturn(subjectDTO);

        ResponseEntity<SubjectDTO> response = subjectController.createSubject(subjectDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(subjectDTO, response.getBody());
        verify(subjectService, times(1)).saveSubject(any(SubjectDTO.class));
    }

    @Test
    void testDeleteSubject() {
        doNothing().when(subjectService).deleteSubject(1L);

        ResponseEntity<Void> response = subjectController.deleteSubject(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(subjectService, times(1)).deleteSubject(1L);
    }
}
