package com.javierlnc.back_app.exception;

public class AreaNotFoundException extends  RuntimeException{
    public AreaNotFoundException (String message){
        super(message);
    }
}
