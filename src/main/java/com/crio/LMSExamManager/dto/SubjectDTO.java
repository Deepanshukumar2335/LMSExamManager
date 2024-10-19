package com.crio.LMSExamManager.dto;

import java.util.List;

public class SubjectDTO {
    private Long subjectId;
    private String subjectName;
    private List<Long> registeredStudentIds; // Only student IDs, not full details

    // Getters and Setters
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

    public List<Long> getRegisteredStudentIds() {
        return registeredStudentIds;
    }

    public void setRegisteredStudentIds(List<Long> registeredStudentIds) {
        this.registeredStudentIds = registeredStudentIds;
    }

}
