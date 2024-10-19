package com.crio.LMSExamManager.dto;

import java.util.List;

public class examDto {
    private Long examId;
    private String examName;
    private Long subjectId; 
    private List<Long> enrolledStudentIds;


    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<Long> getEnrolledStudentIds() {
        return enrolledStudentIds;
    }

    public void setEnrolledStudentIds(List<Long> enrolledStudentIds) {
        this.enrolledStudentIds = enrolledStudentIds;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

}
