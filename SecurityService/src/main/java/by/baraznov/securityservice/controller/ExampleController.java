package by.baraznov.securityservice.controller;

import by.baraznov.securityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
public class ExampleController {

    @GetMapping
    public String example() {
        return "Hello, world!";
    }

}