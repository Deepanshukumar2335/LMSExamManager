package com.crio.LMSExamManager.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.LMSExamManager.repository.SubjectRepository;
import com.crio.LMSExamManager.dto.SubjectDTO;
import com.crio.LMSExamManager.model.Student;
import com.crio.LMSExamManager.model.Subject;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentService studentService;

    // public List<Subject> getAllSubjects(){
    //     return subjectRepository.findAll();
    // }

    // public Subject getSubjectById(Long id){
    //     return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject not found"));
    // }

    public List<SubjectDTO> getAllSubjects(){
        return subjectRepository.findAll().stream()
                .map(this::subjectToDTO)
                .collect(Collectors.toList());
    }

    public SubjectDTO getSubjectById(Long id){
        return subjectRepository.findById(id)
                .map(this::subjectToDTO)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    // public Subject saveSubject(Subject subject){
    //     Optional<Subject> subjectOpt = subjectRepository.findBySubjectName(subject.getSubjectName());
    //     if(subjectOpt.isPresent()){
    //         throw new RuntimeException("Subject with the same Id already exists");
    //     }
    //     return subjectRepository.save(subject);
    // }

    public SubjectDTO saveSubject(SubjectDTO subjectDTO){
        Subject subject = dtoToSubject(subjectDTO);
        Optional<Subject> subjectOpt = subjectRepository.findBySubjectName(subject.getSubjectName());
        if(subjectOpt.isPresent()){
            throw new RuntimeException("Subject with the same name already exists");
        }
        Subject savedSubject = subjectRepository.save(subject);
        return subjectToDTO(savedSubject);
    }

    public void deleteSubject(Long id){
        subjectRepository.deleteById(id);
    }

    private SubjectDTO subjectToDTO(Subject subject){
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectId(subject.getSubjectId());
        subjectDTO.setSubjectName(subject.getSubjectName());
        subjectDTO.setRegisteredStudentIds(
            subject.getRegisteredStudents().stream()
            .map(Student::getStudentId)
            .collect(Collectors.toList())
        );
        return subjectDTO;
    }

    private Subject dtoToSubject(SubjectDTO subjectDTO){
        Subject subject = new Subject();
        subject.setSubjectId(subjectDTO.getSubjectId());
        subject.setSubjectName(subjectDTO.getSubjectName());
        List<Student> students = studentService.getStudentsByIds(subjectDTO.getRegisteredStudentIds());
        subject.setRegisteredStudents(students);
        return subject;
    }
}
