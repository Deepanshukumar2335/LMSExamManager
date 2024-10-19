package com.crio.LMSExamManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.LMSExamManager.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
}
