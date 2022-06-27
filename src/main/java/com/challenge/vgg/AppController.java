package com.challenge.vgg;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("hello")
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping(path = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOrUpdateEntity(@Valid @RequestBody AppRequest appRequest, @PathVariable("username") String username) {
        return appService.createOrUpdateEntity(appRequest, username);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<String> fetchEntity(@PathVariable("username") String username) {
        return appService.fetchEntity(username);
    }
}
