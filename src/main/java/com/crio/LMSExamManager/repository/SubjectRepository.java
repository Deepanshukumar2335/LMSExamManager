package com.crio.LMSExamManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.LMSExamManager.model.Subject;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject , Long> {

    public Optional<Subject> findBySubjectName(String name);
    public Optional<Subject> findBySubjectId(Long subjectId);
    
}
