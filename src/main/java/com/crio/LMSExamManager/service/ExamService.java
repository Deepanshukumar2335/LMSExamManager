package com.crio.LMSExamManager.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.LMSExamManager.dto.examDto;
import com.crio.LMSExamManager.model.Exam;
import com.crio.LMSExamManager.model.Student;
import com.crio.LMSExamManager.model.Subject;
import com.crio.LMSExamManager.repository.ExamRepository;
import com.crio.LMSExamManager.repository.StudentRepository;
import com.crio.LMSExamManager.repository.SubjectRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

        // Get all exams as DTOs
    public List<examDto> getAllExams(){
        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                    .map(this::examToDTO)
                    .collect(Collectors.toList());
    }

    // Get a single exam by ID as a DTO
    public examDto getExamById(Long id){
        return examRepository.findById(id)
                .map(this::examToDTO)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }


    public List<examDto> saveExams(List<examDto> examDTOs) {
        List<examDto> savedExams = new ArrayList<>();

        for (examDto examDTO : examDTOs) {
            
            Subject subject = subjectRepository.findById(examDTO.getSubjectId())
            .orElseThrow(() -> new RuntimeException("Subject not found"));
            
            Exam exam = new Exam();
            exam.setExamName(examDTO.getExamName());

            exam.setSubject(subject);

            // Save each exam and add to the list
            Exam savedExam = examRepository.save(exam);
            savedExams.add(examToDTO(savedExam));
            savedExams.add(examDTO);
        }
        
        //return savedExams;
        return savedExams;
    }


    public void registerStudentForExam(Long studentId,Long examId){
        
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Exam not found"));
        
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        if(!student.getEnrolledSubjects().contains(exam.getSubject())){
            throw new RuntimeException("Student must be enrolled in the subject to register in the exam");
        }

        System.err.println(student);
        System.err.println(exam);

        if(!exam.getEnrolledStudents().contains(student)){
            exam.getEnrolledStudents().add(student);
            //student.getRegisteredExams().add(exam);
            List<Exam> studentRegisteredExams = student.getRegisteredExams();
            studentRegisteredExams.add(exam);

            student.setRegisteredExams(studentRegisteredExams);

            studentRepository.save(student);
    }
    }

    public void deleteExam(Long id){
        examRepository.deleteById(id);
    }

        // Convert Exam entity to DTO
        private examDto examToDTO(Exam exam) {
            examDto examDTO = new examDto();
            examDTO.setExamId(exam.getExamId());
            examDTO.setSubjectId(exam.getSubject().getSubjectId());
            examDTO.setExamName(exam.getExamName());
             examDTO.setEnrolledStudentIds(
                exam.getEnrolledStudents().stream()
                    .map(Student::getStudentId)
                    .collect(Collectors.toList())
            );
            //examDTO.setEnrolledStudentIds(null);  // Handle enrolled students as needed

            return examDTO;
        }
    
        // Convert DTO to Exam entity
        private Exam dtoToExam(examDto examDTO) {
            Exam exam = new Exam();
            exam.setExamId(examDTO.getExamId());
            exam.setExamName(examDTO.getExamName());
            // Set the subject based on subjectId (you need to fetch subject from repository)
            // Set enrolled students (you can fetch the students based on the enrolledStudentIds)
            return exam;
        }
    
}
