package com.crio.LMSExamManager.service;

import com.crio.LMSExamManager.dto.SubjectDTO;
import com.crio.LMSExamManager.model.Subject;
import com.crio.LMSExamManager.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        when(subjectRepository.findAll()).thenReturn(subjects);

        List<Long> studentIds = new ArrayList<>();
        studentIds.add(1L);
        when(studentService.getStudentsByIds(studentIds)).thenReturn(new ArrayList<>());

        List<SubjectDTO> subjectDTOs = subjectService.getAllSubjects();
        assertFalse(subjectDTOs.isEmpty());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void testGetSubjectById() {
        Long subjectId = 1L;
        Subject subject = new Subject();
        subject.setSubjectId(subjectId);
        subject.setSubjectName("Mathematics");
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        SubjectDTO result = subjectService.getSubjectById(subjectId);
        assertNotNull(result);
        assertEquals(subjectId, result.getSubjectId());
        assertEquals("Mathematics", result.getSubjectName());
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void testSaveSubject() {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectName("Mathematics");

        Subject subject = new Subject();
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDTO savedSubjectDTO = subjectService.saveSubject(subjectDTO);
        assertNotNull(savedSubjectDTO);
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }
}
