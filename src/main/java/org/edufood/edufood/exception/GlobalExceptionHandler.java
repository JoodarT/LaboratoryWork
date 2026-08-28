package org.edufood.edufood.exception;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            NoResourceFoundException.class
    })
    public String handleNotFound(Exception ex, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        logOnce(request, () -> log.warn("404 на {} {} — {}",
                request.getMethod(), request.getRequestURI(), ex.getMessage()));
        model.addAttribute("status", 404);
        model.addAttribute("message", ex instanceof IllegalArgumentException ? ex.getMessage() : null);
        return "error/404";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatus(ResponseStatusException ex, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {
        int status = ex.getStatusCode().value();
        response.setStatus(status);
        logOnce(request, () -> log.warn("{} на {} {} — {}",
                status, request.getMethod(), request.getRequestURI(), ex.getReason()));
        model.addAttribute("status", status);
        model.addAttribute("message", ex.getReason());
        return status >= 500 ? "error/500" : "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpected(Exception ex, HttpServletRequest request, Model model) {
        logOnce(request, () -> log.error("Необработанная ошибка при {} {}",
                request.getMethod(), request.getRequestURI(), ex));
        model.addAttribute("status", 500);
        return "error/500";
    }

    private void logOnce(HttpServletRequest request, Runnable logging) {
        if (request.getDispatcherType() == DispatcherType.REQUEST) {
            logging.run();
        }
    }
}
