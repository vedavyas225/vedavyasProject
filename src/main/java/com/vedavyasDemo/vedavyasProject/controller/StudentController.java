package com.vedavyasDemo.vedavyasProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedavyasDemo.vedavyasProject.Model.Student;
import com.vedavyasDemo.vedavyasProject.Service.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 if not found
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    	System.out.println("Student is created" +student);
        Student createdStudent = studentService.addStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student updatedStudent, @PathVariable int id) {
        Optional<Student> updated = studentService.updateStudent(id, updatedStudent);
        return updated
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 if not found
    }


//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
//        if (studentService.deleteStudent(id)) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if not found
//        }
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable int id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting student with id: " + id);
        }
    }
}
