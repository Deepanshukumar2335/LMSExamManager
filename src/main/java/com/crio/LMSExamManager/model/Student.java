package com.crio.LMSExamManager.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String studentName;

    @ManyToMany
    @JoinTable(
        name = "student_subject",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    @JsonManagedReference
    private List<Subject> enrolledSubjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "student_exam",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<Exam> registeredExams = new ArrayList<>();
    

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }
    public void setEnrolledSubjects(List<Subject> enrolledSubjects) {
        this.enrolledSubjects = enrolledSubjects;
    }
    public List<Exam> getRegisteredExams() {
        return registeredExams;
    }
    public void setRegisteredExams(List<Exam> registeredExams) {
        this.registeredExams = registeredExams;
    }
    
    

}
