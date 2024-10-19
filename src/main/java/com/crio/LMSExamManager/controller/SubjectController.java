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

import com.crio.LMSExamManager.dto.SubjectDTO;
import com.crio.LMSExamManager.service.SubjectService;

import java.util.*;


@RestController
@RequestMapping("/subjects")
public class SubjectController {
    
@Autowired
private SubjectService subjectService;


@GetMapping
public ResponseEntity<List<SubjectDTO>> getAllSubjects(){

    return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
}

@GetMapping("/{id}")
public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id){

    // SubjectDTO subjectDTO = subjectService.getSubjectById(id);
    // if(subjectDTO != null){
    //     return ResponseEntity.ok(subjectDTO);
    // } else {
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    // }
    try{
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subjectDTO);
    } catch(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

@PostMapping
public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subjectDTO){
    SubjectDTO savedSubjectDTO = subjectService.saveSubject(subjectDTO);
    
    return ResponseEntity.status(HttpStatus.CREATED).body(savedSubjectDTO);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteSubject(@PathVariable Long id){
    subjectService.deleteSubject(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
}
}
