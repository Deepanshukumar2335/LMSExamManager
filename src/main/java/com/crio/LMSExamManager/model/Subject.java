package com.crio.LMSExamManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    
    private String subjectName;

    @ManyToMany(mappedBy = "enrolledSubjects")
    @JsonBackReference
    private List<Student> registeredStudents = new ArrayList<>();

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    

}
