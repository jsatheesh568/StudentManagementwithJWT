package com.studentmangement.StudentManagement.service;

import com.studentmangement.StudentManagement.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student updatedStudent);

    void deleteStudent(Long id);
}
