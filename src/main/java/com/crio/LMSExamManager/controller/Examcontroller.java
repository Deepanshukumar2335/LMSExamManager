package com.crio.LMSExamManager.controller;

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
import java.util.*;

import com.crio.LMSExamManager.service.ExamService;

import com.crio.LMSExamManager.dto.examDto;
@RestController
@RequestMapping("/exams")
public class Examcontroller {
    
    @Autowired
    private ExamService examService;


    // Get all exams as DTOs
    @GetMapping
    public ResponseEntity<List<examDto>> getAllExams(){
        return ResponseEntity.ok(examService.getAllExams());
    }

    // Get a single exam by ID as a DTO
    @GetMapping("/{id}")
    public ResponseEntity<examDto> getExamById(@PathVariable Long id){
        try {
            examDto exam = examService.getExamById(id);
            return ResponseEntity.ok(exam);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    // Create a new exam with a DTO
    @PostMapping
    public ResponseEntity<List<examDto>> createExam(@RequestBody List<examDto> examDTOs){
        List<examDto> savedExam = examService.saveExams(examDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExam);
    }

    @PostMapping("/register/{studentId}/{examId}")
    public ResponseEntity<String> registerStudentForExam(@PathVariable Long studentId, @PathVariable Long examId) {
        examService.registerStudentForExam(studentId, examId);
        return ResponseEntity.status(HttpStatus.OK).body("Student registered for the exam successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExam(@PathVariable Long id){
        examService.deleteExam(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
