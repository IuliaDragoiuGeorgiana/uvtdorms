package com.uvtdorms.exception;

public class WashingMachineNotFoundException extends Exception{
    public WashingMachineNotFoundException(){
        super("washing_machine_not_found");
    }
}
