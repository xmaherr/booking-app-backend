package com.maher.bookingapp.Exceptions;

public class WrongEmailOrPassword extends RuntimeException {

    public WrongEmailOrPassword(String message) {
        super(message);
    }
}