package org.edufood.edufood.controller;

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

/**
 * Глобальный обработчик исключений при обработке HTTP-запросов.
 * Ожидаемые ошибки (не найдено, некорректный параметр пути) логируются на уровне WARN,
 * всё непредвиденное — на уровне ERROR со stacktrace. Пользователю отдаётся аккуратная
 * страница ошибки вместо стандартного whitelabel.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /** Ресурс не найден / неизвестный URL / некорректный параметр пути — 404, лог WARN. */
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

    /** Явно выброшенный ResponseStatusException — уважаем его статус. */
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

    /** Любая непредвиденная ошибка — 500, лог ERROR со stacktrace. */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpected(Exception ex, HttpServletRequest request, Model model) {
        logOnce(request, () -> log.error("Необработанная ошибка при {} {}",
                request.getMethod(), request.getRequestURI(), ex));
        model.addAttribute("status", 500);
        return "error/500";
    }

    /** Логируем только на исходном запросе, не на вложенном ERROR-dispatch контейнера. */
    private void logOnce(HttpServletRequest request, Runnable logging) {
        if (request.getDispatcherType() == DispatcherType.REQUEST) {
            logging.run();
        }
    }
}
