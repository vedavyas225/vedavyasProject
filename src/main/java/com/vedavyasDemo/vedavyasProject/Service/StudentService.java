package com.vedavyasDemo.vedavyasProject.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vedavyasDemo.vedavyasProject.Model.Student;
import com.vedavyasDemo.vedavyasProject.Repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> updateStudent(int id, Student updatedStudent) {
        return studentRepository.findById(id)
            .map(existingStudent -> {
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setRollNumber(updatedStudent.getRollNumber());
                existingStudent.setSubject(updatedStudent.getSubject());
                return studentRepository.save(existingStudent);
            });
    }

    public boolean deleteStudent(int id) {
        try {
            studentRepository.deleteById(id);
            return true; // Deletion successful
        } catch (EmptyResultDataAccessException e) {
            return false; // Student not found
        }
    }
}
