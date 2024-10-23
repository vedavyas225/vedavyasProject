package com.vedavyasDemo.vedavyasProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedavyasDemo.vedavyasProject.Model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
