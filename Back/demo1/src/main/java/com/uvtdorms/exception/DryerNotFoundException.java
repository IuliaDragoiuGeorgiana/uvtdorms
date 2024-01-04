package com.uvtdorms.exception;

public class DryerNotFoundException extends Exception{
    public DryerNotFoundException(){
        super("dryer_not_found");
    }
}
