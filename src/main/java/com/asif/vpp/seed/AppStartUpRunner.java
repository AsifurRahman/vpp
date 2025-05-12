package com.asif.vpp.seed;

import com.asif.vpp.model.Role;
import com.asif.vpp.model.User;
import com.asif.vpp.repository.RoleRepository;
import com.asif.vpp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class AppStartUpRunner implements CommandLineRunner {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
            System.out.println("✅ Default role created: " + role.getName());
        }

        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("$2a$12$09iUnsV29p.xVdc53DMvG.b9NjsnUMlRM1CyfeJIiPvtJd1qUiiPa");//pwd: "admin"

            Role role = roleRepository.findByNameIgnoreCase("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
            userRepository.save(user);
            System.out.println("✅ Default user created: " + user.getUsername());
        }
    }
}
