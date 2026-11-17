package org.dides.studentmanagement.config;

import org.dides.studentmanagement.enums.Roles;
import org.dides.studentmanagement.model.Users;
import org.dides.studentmanagement.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder;

    public AdminSeeder(UserRepo repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (repo.findByEmail("admin@dides.local") == null) {
            Users admin = new Users();
            admin.setFirstname("System");
            admin.setLastname("Admin");
            admin.setEmail("admin@dides.local");
            admin.setPassword(encoder.encode("ChangeMe!123"));
            admin.setRole(Roles.ADMIN);
            repo.save(admin);
        }
    }
}
