package com.indramakers.example.measuresms.excepciones;
//clase de manejo de excepciones

import com.indramakers.example.measuresms.model.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice //el intercepta las peticiones que llegan y salen del servicio,
public class CustomExceptionHandler {

    //se crea metodo independiente con respuesta con cuerpo personalizado


    @ResponseStatus(HttpStatus.PRECONDITION_FAILED) //define el status a enviar
    @ResponseBody //la respuesta de error va a ser personalizda
    @ExceptionHandler(BussinessException.class)//esta notacion le dice al metodo que se invoque cuando ocurre bussinesExceptoon
    public ErrorResponse handlerBussinesExcepion(BussinessException exception){
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler
    public ErrorResponse handleNotFoundException(NotFoundException exception){
        return new ErrorResponse("404", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleNotFoundException(MethodArgumentNotValidException exception){
        return new ErrorResponse("400", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleException(Exception exception){
        return new ErrorResponse("500", exception.getMessage());
    }







}
