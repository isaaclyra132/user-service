package br.tre.userservice.entities.dto;

import br.tre.userservice.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NewUserDTO {
    private String name;
    private String email;
    private Set<Role> roles;
}
