package com.crio.LMSExamManager.controller;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.LMSExamManager.dto.StudentDTO;
import com.crio.LMSExamManager.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        StudentDTO student = studentService.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO){
        StudentDTO savedStudent = studentService.saveStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PostMapping("/{studentId}/enroll")
    public ResponseEntity<String> enrollStudentInSubjects(@PathVariable Long studentId, @RequestBody List<Long> subjectIds) {
    try {
        studentService.enrollStudentInSubjects(studentId, subjectIds);
        return ResponseEntity.status(HttpStatus.OK).body("Student enrolled in subjects successfully.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error enrolling student in subjects.");
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
