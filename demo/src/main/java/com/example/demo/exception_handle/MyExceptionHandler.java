//package com.example.demo.exception_handle;
//
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class MyExceptionHandler {
//
//    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleNotFoundException(ChangeSetPersister.NotFoundException exception, Model model) {
//        model.addAttribute("errorMessage", "404 Not found");
//        return "error";
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleInternalServerError(Exception exception, Model model) {
//        model.addAttribute("errorMessage", "500 Internal Server Error");
//        return "error";
//    }
//}