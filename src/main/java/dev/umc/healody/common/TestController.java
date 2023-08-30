package dev.umc.healody.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth/test")
@RestController
public class TestController {

    @GetMapping
    public String testMethod() {
        return "Hello world!";
    }
}
