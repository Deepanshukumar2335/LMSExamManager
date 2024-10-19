package com.crio.LMSExamManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.crio.LMSExamManager.repository.StudentRepository;
import com.crio.LMSExamManager.repository.SubjectRepository;
import com.crio.LMSExamManager.dto.StudentDTO;
import com.crio.LMSExamManager.model.Student;
import com.crio.LMSExamManager.model.Subject;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // public List<Student> getAllStudents(){
    //     return studentRepository.findAll();
    // }
    
        // Get all students as DTOs
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::studentToDTO)
                .collect(Collectors.toList());
    }

    
    // public Student getStudentById(Long id){
        //     return studentRepository.findById(id).orElse(null);
        // }
        
        // Get student by ID as DTO
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student != null ? studentToDTO(student) : null;
    }

    public List<Student> getStudentsByIds(List<Long> studentIds){
        List<Student> students = studentRepository.findAllById(studentIds);

        if(students.isEmpty()){
            throw new RuntimeException("No valid student for the given Ids");
        }
        
        return students;
    }


    // public Student saveStudent(Student student){
    //     return studentRepository.save(student);
    // }

         // Save student using DTO
        public StudentDTO saveStudent(StudentDTO studentDTO) {
            Student student = dtoToStudent(studentDTO);
            Student savedStudent = studentRepository.save(student);
            return studentToDTO(savedStudent);
        }
    

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }
    
    public void enrollStudentInSubjects(Long studentId, List<Long> subjectIds) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        if (subjects.isEmpty()) {
            throw new RuntimeException("No valid subjects found");
        }

        student.getEnrolledSubjects().addAll(subjects);  
        studentRepository.save(student);
    }

        // Convert Student entity to DTO
        private StudentDTO studentToDTO(Student student) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudentId(student.getStudentId());
            studentDTO.setStudentName(student.getStudentName());
            studentDTO.setEnrolledSubjectIds(
                student.getEnrolledSubjects().stream()
                       .map(Subject::getSubjectId)
                       .collect(Collectors.toList())
            );
            return studentDTO;
        }
    
        // Convert DTO to Student entity
        private Student dtoToStudent(StudentDTO studentDTO) {
            Student student = new Student();
            student.setStudentId(studentDTO.getStudentId());
            student.setStudentName(studentDTO.getStudentName());
            return student;
        }
    

}
