package com.crio.LMSExamManager.dto;

import java.util.List;

public class StudentDTO {
    private Long studentId;
    private String studentName;
    private List<Long> enrolledSubjectIds; 

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

    public List<Long> getEnrolledSubjectIds() {
        return enrolledSubjectIds;
    }

    public void setEnrolledSubjectIds(List<Long> enrolledSubjectIds) {
        this.enrolledSubjectIds = enrolledSubjectIds;
    }
}


