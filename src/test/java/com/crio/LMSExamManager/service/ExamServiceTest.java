package com.crio.LMSExamManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crio.LMSExamManager.dto.examDto;
import com.crio.LMSExamManager.model.Exam;
import com.crio.LMSExamManager.model.Student;
import com.crio.LMSExamManager.model.Subject;
import com.crio.LMSExamManager.repository.ExamRepository;
import com.crio.LMSExamManager.repository.StudentRepository;
import com.crio.LMSExamManager.repository.SubjectRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ExamServiceTest {
    
    @Mock
    private ExamRepository examRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock 
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllExams(){
        // Create a mock Subject
        Subject mockSubject = new Subject();
        mockSubject.setSubjectId(1L);


        //Mock Exam with valid Subject
        List<Exam> mockExams = new ArrayList<>();
        Exam exam = new Exam();
        exam.setSubject(mockSubject);
        mockExams.add(exam);

        when(examRepository.findAll()).thenReturn(mockExams);
    
        //Execute service call
        List<examDto> examDtos = examService.getAllExams();

        //Verify interactions and assert
        assertFalse(examDtos.isEmpty());
        verify(examRepository, times(1)).findAll();
    }

    @Test
    void testGetExamById(){
        Long examId =1L;

        // Create a mock Subject
        Subject mockSubject = new Subject();
        mockSubject.setSubjectId(1L);
        
        //Mock Exam with valid subject
        Exam exam = new Exam();
        exam.setExamId(examId);
        exam.setSubject(mockSubject);

        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        
        //Execute service call
        examDto result = examService.getExamById(examId);

        //Verify and assert
        assertNotNull(result);
        assertEquals(examId,result.getExamId());

        verify(examRepository,times(1)).findById(examId);
    }

    @Test
    void testSaveExams(){
        //create examDto
        examDto examDto = new examDto();
        examDto.setExamName("Math Final Exam");
        examDto.setSubjectId(1L);

        List<examDto> examDtos = new ArrayList<>();
        examDtos.add(examDto);

        //Mock Subject
        Subject mockSubject = new Subject();
        mockSubject.setSubjectId(1L);

        //Mock Subject Respository response
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(mockSubject));
        
        //create mock Exam
        Exam exam = new Exam();
        exam.setSubject(mockSubject);

        //Mock Exam Respository response
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        //Execute service call
        List<examDto> savedExams = examService.saveExams(examDtos);

        //Verify and assert
        assertFalse(savedExams.isEmpty());
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test
    void testRegisterStudentForExam(){
        Long studentId = 1L;
        Long examId = 1L;

        // Create mock Subject
        Subject mockSubject = new Subject();
        mockSubject.setSubjectId(1L);

        // Mock Exam with valid Subject
        Exam exam = new Exam();
        exam.setSubject(mockSubject);

        // Mock Student
        Student student = new Student();
        student.setStudentId(studentId);
        List<Subject> enrolledSubjects = new ArrayList<>();
        enrolledSubjects.add(mockSubject);
        student.setEnrolledSubjects(enrolledSubjects);

        // Mock Repository responses
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // Execute service call
        examService.registerStudentForExam(studentId, examId);

        // Verify interactions
        verify(examRepository, times(1)).findById(examId);
        verify(studentRepository, times(1)).findById(studentId);
    }


}
