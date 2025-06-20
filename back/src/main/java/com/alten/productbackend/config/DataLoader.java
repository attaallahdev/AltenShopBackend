package com.alten.productbackend.config;

import com.alten.productbackend.model.User;
import com.alten.productbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByEmail("admin@admin.com").isPresent()) {
            User adminUser = new User();
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword(passwordEncoder.encode("alten1234@@@@"));
            adminUser.setUsername("admin");
            userRepository.save(adminUser);
            System.out.println(">>> Utilisateur admin créé avec l'email 'admin@admin.com' et le mot de passe 'alten1234@@@@'");
        }
    }
}