package com.vedavyasDemo.vedavyasProject.ControllerTests;

import com.vedavyasDemo.vedavyasProject.Model.Student;
import com.vedavyasDemo.vedavyasProject.Service.StudentService;
import com.vedavyasDemo.vedavyasProject.controller.StudentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@AutoConfigureMockMvc
//@SpringBootTest
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
	@Autowired
	public MockMvc mockMvc;
	
	
	// test data not real data 
	@MockBean
	public StudentService studentService;
	
	@InjectMocks
	public StudentController studentController;
	
	private Student student;
	
	@BeforeEach
	void setUp() {
		
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testingGetAllStudents() throws Exception {
		Student student = new Student();
		student.setId(1);
		student.setName("veda-test");
		student.setRollNumber(99);
		student.setSubject("test-subject");
		
		List<Student> students = Arrays.asList(student);
		when(studentService.getAllStudents()).thenReturn(students);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/student"))
	    .andExpect(status().isOk())
	    .andExpect(jsonPath("$[0].name").value("veda-test"))
	    .andExpect(jsonPath("$[0].rollNumber").value(99))
	    .andExpect(jsonPath("$[0].subject").value("test-subject"));
	}
	
	@Test
	public void testingCreateStudent() throws Exception {
		Student student = new Student();
		student.setId(1);
		student.setName("veda-test");
		student.setRollNumber(99);
		student.setSubject("test-subject");
		
		when(studentService.addStudent(student)).thenReturn(student);
		
		mockMvc.perform(post("/student")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"veda-test\",\"rollNumber\":99,\"subject\":\"test-subject\"}"))
				.andExpect(status().isCreated());
				
	}
	
	@Test
	public void testingGetStudentById() throws Exception{
		Student student = new Student();
		student.setId(1);
		student.setName("veda-test");
		student.setRollNumber(99);
		student.setSubject("test-subject");
		
		when(studentService.getStudentById(1)).thenReturn(Optional.of(student));
		
		mockMvc.perform(get("/student/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("veda-test"))
		.andExpect(jsonPath("$.rollNumber").value(99))
		.andExpect(jsonPath("$.subject").value("test-subject"));
		
	}
	
	@Test
	public void testUpdateStudent() throws Exception {
	    // Setup the existing student
	    Student existingStudent = new Student();
	    existingStudent.setId(1);
	    existingStudent.setName("veda-test");
	    existingStudent.setRollNumber(99);
	    existingStudent.setSubject("test-subject");

	    // Setup the updated student
	    Student updatedStudent = new Student();
	    updatedStudent.setName("updated-veda-test");
	    updatedStudent.setRollNumber(99);
	    updatedStudent.setSubject("test-subject");

	    // Mocking the behavior for existing student retrieval
	    when(studentService.getStudentById(1)).thenReturn(Optional.of(existingStudent));
	    // Mocking the update behavior to return the updated student
	    when(studentService.updateStudent(eq(1), any(Student.class))).thenReturn(Optional.of(updatedStudent));

	    mockMvc.perform(put("/student/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("{\"name\":\"updated-veda-test\",\"rollNumber\":99,\"subject\":\"test-subject\"}"))
	            .andExpect(status().isOk())  // Expecting 200 OK
	            .andExpect(jsonPath("$.name").value("updated-veda-test"))
	            .andExpect(jsonPath("$.rollNumber").value(99))
	            .andExpect(jsonPath("$.subject").value("test-subject"));
	}



	
	@Test
	public void testingDeleteStudent() throws Exception {
        when(studentService.deleteStudent(1)).thenReturn(true);
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
	}

}
