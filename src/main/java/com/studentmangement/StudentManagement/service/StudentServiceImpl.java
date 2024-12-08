package com.studentmangement.StudentManagement.service;

import com.studentmangement.StudentManagement.exception.StudentNotFoundException;
import com.studentmangement.StudentManagement.model.Student;
import com.studentmangement.StudentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Student with ID " + id + " not found"));
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = getStudentById(id); // Ensure student exists
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setGrade(updatedStudent.getGrade());
        existingStudent.setActivity(updatedStudent.getActivity());
        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student existingStudent = getStudentById(id);
        studentRepository.delete(existingStudent);
    }
}
