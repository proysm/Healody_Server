package dev.umc.healody.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping
    public SuccessResponse testMethod() {
        return new SuccessResponse<>(SuccessStatus.SUCCESS);
    }
}
