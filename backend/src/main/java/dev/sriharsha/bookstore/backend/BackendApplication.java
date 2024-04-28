package dev.sriharsha.bookstore.backend;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import dev.sriharsha.bookstore.backend.entity.Role;
import dev.sriharsha.bookstore.backend.repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            var admin = Role.builder().id(600).name("ADMIN").build();
            var user = Role.builder().id(601).name("USER").build();
            roleRepository.saveAll(List.of(admin, user));
        };
    }

}