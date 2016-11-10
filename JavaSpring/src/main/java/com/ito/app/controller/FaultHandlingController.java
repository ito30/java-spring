package com.ito.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snail.core.beans.DeliveryMap;
import com.snail.core.fault.Fault;

@ControllerAdvice
public class FaultHandlingController {
	
	@ExceptionHandler(Fault.class)
    public @ResponseBody DeliveryMap defaultErrorHandler(HttpServletRequest request, Fault f) {
		f.printStackTrace();
        return f.deliver();
    }
}
