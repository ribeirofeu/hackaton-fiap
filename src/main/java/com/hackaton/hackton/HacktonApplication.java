package com.hackaton.hackton;

import com.hackaton.hackton.model.Email;
import com.hackaton.hackton.model.entity.UserEntity;
import com.hackaton.hackton.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@SpringBootApplication
public class HacktonApplication {
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HacktonApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			var optionalUser = userRepository.findByEmailOrUsername("teste@hackathon.com");
			if (optionalUser.isEmpty()) {
				userRepository.save(UserEntity.builder()
						.email(Email.of("teste@hackathon.com"))
						.name("Teste")
						.username("teste")
						.password(passwordEncoder.encode("123456"))
						.build());
			}
		};
	}

}
