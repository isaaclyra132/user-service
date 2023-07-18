package br.tre.userservice.entities.dto;

import java.util.List;

public record UserRegisterRequest(String name, String email, String password, List<String> roles) {
}
