package com.example.doan.data.model;

public class ErrorSignup {
    private int error;
    private ErrorMessage message[];

    public ErrorSignup(int error, ErrorMessage[] message) {
        this.error = error;
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public ErrorMessage[] getMessage() {
        return message;
    }
}
