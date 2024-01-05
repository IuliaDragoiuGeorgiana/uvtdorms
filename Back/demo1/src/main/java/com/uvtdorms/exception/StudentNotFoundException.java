package com.uvtdorms.exception;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException()
    {
        super("student_not_found");
    }
}
