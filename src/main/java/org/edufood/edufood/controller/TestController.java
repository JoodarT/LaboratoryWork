package org.edufood.edufood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Временный контроллер только для проверки layout.ftlh в браузере.
// Удалить после того, как появится настоящий RestaurantController (задача #3).
@Controller
public class TestController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
