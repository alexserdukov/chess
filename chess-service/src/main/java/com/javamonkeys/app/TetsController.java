package com.javamonkeys.app;

/**
 * Created by scream on 01.04.2015.
 */

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TetsController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
