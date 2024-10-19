package com.crio.LMSExamManager.service;

import com.crio.LMSExamManager.model.Student;
import com.crio.LMSExamManager.model.Subject;
import com.crio.LMSExamManager.dto.StudentDTO;
import com.crio.LMSExamManager.repository.StudentRepository;
import com.crio.LMSExamManager.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test getAllStudents()
    @Test
    public void testGetAllStudents() {
        // Arrange
        Student student1 = new Student();
        student1.setStudentId(1L);
        student1.setStudentName("John Doe");

        Student student2 = new Student();
        student2.setStudentId(2L);
        student2.setStudentName("Jane Doe");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<StudentDTO> studentDTOs = studentService.getAllStudents();

        // Assert
        assertEquals(2, studentDTOs.size());
        assertEquals("John Doe", studentDTOs.get(0).getStudentName());
        assertEquals("Jane Doe", studentDTOs.get(1).getStudentName());
    }

    // Test getStudentById() - valid student
    @Test
    public void testGetStudentById_Valid() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1L);
        student.setStudentName("John Doe");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        StudentDTO studentDTO = studentService.getStudentById(1L);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(1L, studentDTO.getStudentId());
        assertEquals("John Doe", studentDTO.getStudentName());
    }

    // Test getStudentById() - student not found
    @Test
    public void testGetStudentById_NotFound() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        StudentDTO studentDTO = studentService.getStudentById(1L);

        // Assert
        assertNull(studentDTO);
    }

    // Test saveStudent()
    @Test
    public void testSaveStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(1L);
        studentDTO.setStudentName("John Doe");

        Student student = new Student();
        student.setStudentId(1L);
        student.setStudentName("John Doe");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        StudentDTO savedStudentDTO = studentService.saveStudent(studentDTO);

        // Assert
        assertNotNull(savedStudentDTO);
        assertEquals(1L, savedStudentDTO.getStudentId());
        assertEquals("John Doe", savedStudentDTO.getStudentName());
    }

    // Test enrollStudentInSubjects()
    @Test
    public void testEnrollStudentInSubjects() {
        // Arrange
        Student student = new Student();
        student.setStudentId(1L);
        student.setStudentName("John Doe");

        Subject subject1 = new Subject();
        subject1.setSubjectId(101L);
        subject1.setSubjectName("Math");

        Subject subject2 = new Subject();
        subject2.setSubjectId(102L);
        subject2.setSubjectName("Science");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findAllById(anyList())).thenReturn(Arrays.asList(subject1, subject2));

        // Act
        studentService.enrollStudentInSubjects(1L, Arrays.asList(101L, 102L));

        // Assert
        assertEquals(2, student.getEnrolledSubjects().size());
        verify(studentRepository, times(1)).save(student);
    }

    // Test getStudentsByIds()
    @Test
    public void testGetStudentsByIds() {
        // Arrange
        Student student1 = new Student();
        student1.setStudentId(1L);
        student1.setStudentName("John Doe");

        Student student2 = new Student();
        student2.setStudentId(2L);
        student2.setStudentName("Jane Doe");

        when(studentRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<Student> students = studentService.getStudentsByIds(Arrays.asList(1L, 2L));

        // Assert
        assertEquals(2, students.size());
        assertEquals("John Doe", students.get(0).getStudentName());
        assertEquals("Jane Doe", students.get(1).getStudentName());
    }
}
