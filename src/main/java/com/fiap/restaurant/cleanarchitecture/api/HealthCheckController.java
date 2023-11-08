package com.fiap.restaurant.cleanarchitecture.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<Object> index() {
        HashMap<String, String> map = new HashMap<>();
        map.put("health", "true");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
