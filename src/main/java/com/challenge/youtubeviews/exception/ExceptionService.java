package com.challenge.youtubeviews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionService {
    @ExceptionHandler(VideoNotFoundException.class)
    ResponseEntity<VideoNotFoundException> handleException(VideoNotFoundException err){
        UserErrorResponse uer = new UserErrorResponse();
        uer.setStatus(HttpStatus.NOT_FOUND.value());
        uer.setMessage("No video found with the provided ID.");
        return new ResponseEntity(uer, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VideoAlreadySavedInDbException.class)
    ResponseEntity<VideoAlreadySavedInDbException> handleException(VideoAlreadySavedInDbException err){
        UserErrorResponse uer = new UserErrorResponse();
        uer.setStatus(HttpStatus.BAD_REQUEST.value());
        uer.setMessage("The video was already added to the database.");
        return new ResponseEntity(uer, HttpStatus.BAD_REQUEST);
    }

}
