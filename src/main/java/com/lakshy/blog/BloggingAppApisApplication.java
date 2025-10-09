package com.lakshy.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lakshy.blog.config.AppConstants;
import com.lakshy.blog.entities.Role;
import com.lakshy.blog.repositories.RoleRepo;

@SpringBootApplication
public class BloggingAppApisApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BloggingAppApisApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // This enables deployment to external Tomcat (WAR packaging)
        return builder.sources(BloggingAppApisApplication.class);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        // Uncomment to print encoded password
        // System.out.println(this.passwordEncoder.encode("12345"));

        try {
            // Create two roles: ADMIN and NORMAL
            Role roleAdmin = new Role();
            roleAdmin.setId(AppConstants.ADMIN_USER);
            roleAdmin.setName("ROLE_ADMIN");

            Role roleNormal = new Role();
            roleNormal.setId(AppConstants.NORMAL_USER);
            roleNormal.setName("ROLE_NORMAL");

            List<Role> roles = List.of(roleAdmin, roleNormal);

            List<Role> savedRoles = this.roleRepo.saveAll(roles);

            savedRoles.forEach(r -> System.out.println(r.getName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
