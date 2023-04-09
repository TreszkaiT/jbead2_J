package com.tt.jbead.exceptions;

import java.util.List;

public class InvalidEntityException extends RuntimeException{

    private List<String> messages;

    public InvalidEntityException(String message, List<String> messages) {
        super(message);
        this.messages = messages;
    }

//    @Override
//    public List<String> getMessage() {return messages}
}
