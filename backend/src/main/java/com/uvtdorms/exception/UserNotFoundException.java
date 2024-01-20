package com.uvtdorms.exception;

public class UserNotFoundException extends ServiceException{
    public UserNotFoundException(){
        super("user_not_found");
    }
}
