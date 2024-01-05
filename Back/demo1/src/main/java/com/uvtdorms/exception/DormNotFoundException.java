package com.uvtdorms.exception;

public class DormNotFoundException extends Exception {
    public DormNotFoundException(){
        super("dorm_not_found");
    }
}
