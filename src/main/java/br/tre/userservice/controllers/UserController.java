package br.tre.userservice.controllers;

import br.tre.userservice.entities.User;
import br.tre.userservice.entities.dto.UserRegisterRequest;
import br.tre.userservice.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private UserService service;

    UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User obj = service.findById(id);
        return ResponseEntity.ok(obj);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        User obj = service.findByEmail(email);
        return ResponseEntity.ok(obj);
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterRequest newUser) throws JsonProcessingException {
        return ResponseEntity.ok().body(service.save(newUser));
    }

    @GetMapping(value = "/health")
    private ResponseEntity<String> health() {
        return new ResponseEntity<>("Estou funcionando!", HttpStatus.OK);
    }
}
