package br.tre.userservice.controllers;

import br.tre.userservice.entities.Role;
import br.tre.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private RoleRepository repository;

    RoleController(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    @PostMapping()
    public ResponseEntity<Role> newRole(@RequestBody Role newRole){
        return ResponseEntity.ok(repository.save(newRole));
    }
}
