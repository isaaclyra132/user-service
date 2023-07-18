package br.tre.userservice.services;

import br.tre.userservice.config.RabbitMQConfig;
import br.tre.userservice.entities.Role;
import br.tre.userservice.entities.User;
import br.tre.userservice.entities.dto.NewUserDTO;
import br.tre.userservice.entities.dto.UserRegisterRequest;
import br.tre.userservice.repository.RoleRepository;
import br.tre.userservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;
    private RoleRepository roleRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    private final ObjectMapper objectMapper;
    private BCryptPasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, RoleRepository roleRepository, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, BCryptPasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.roleRepository = roleRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    public List<User> findAll () {
        return repository.findAll();
    }

    public User save(UserRegisterRequest newUserDTO) throws JsonProcessingException {
        Set<Role> roles = new HashSet<>(newUserDTO.roles().stream().map(role -> roleRepository.findByRoleName(role).orElseThrow()).toList());

        User newUser = new User();
        newUser.setEmail(newUserDTO.email());
        newUser.setName(newUserDTO.name());
        newUser.setPassword( passwordEncoder.encode(newUserDTO.email()));
        newUser.setRoles(roles);

        sendMessageNewUser(newUser);

        return repository.save(newUser);
    }

    private void sendMessageNewUser(User user) throws JsonProcessingException {
        NewUserDTO newUser = new NewUserDTO();

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setRoles(user.getRoles());

        String requestJson = objectMapper.writeValueAsString(newUser);
        rabbitTemplate.convertAndSend(queue, requestJson);
    }

}
