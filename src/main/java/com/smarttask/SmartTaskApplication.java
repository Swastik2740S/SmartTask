package com.smarttask;

import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.dto.TaskResponseDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.enums.Role;
import com.smarttask.model.Project;
import com.smarttask.model.Task;
import com.smarttask.model.User;
import com.smarttask.model.UserTeam;
import com.smarttask.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableMethodSecurity
@EnableWebMvc
@SpringBootApplication
public class SmartTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartTaskApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<UserTeam, UserTeamResponseDTO>() {
			@Override
			protected void configure() {
				// Safe userId mapping
				using(ctx -> {
					UserTeam source = (UserTeam) ctx.getSource();
					return (source.getUser() != null) ? source.getUser().getUserId() : null;
				}).map(source, destination.getUserId());

				// Safe teamId mapping
				using(ctx -> {
					UserTeam source = (UserTeam) ctx.getSource();
					return (source.getTeam() != null) ? source.getTeam().getTeamId() : null;
				}).map(source, destination.getTeamId());

				map().setRole(source.getRole());
			}
		});



		// Project mapping with null safety
		modelMapper.addMappings(new PropertyMap<Project, ProjectResponseDTO>() {
			@Override
			protected void configure() {
				map().setTeamId(source.getTeam() != null ? source.getTeam().getTeamId() : null);
			}
		});

		// Task mapping with custom converter for assignedTo and null safety
		modelMapper.addMappings(new PropertyMap<Task, TaskResponseDTO>() {
			@Override
			protected void configure() {
				map().setProjectId(source.getProject().getProjectId());
				using(ctx -> {
					User user = ((Task) ctx.getSource()).getAssignedTo();
					return user != null ? user.getUserId() : null;
				}).map(source, destination.getAssignedTo());
			}
		});


		modelMapper.typeMap(Project.class, ProjectResponseDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getTeam().getTeamId(), ProjectResponseDTO::setTeamId);
		});

		return modelMapper;
	}

	@Bean
	public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminEmail = "admin@example.com";
			if (userRepository.findByEmail(adminEmail).isEmpty()) {
				User admin = new User();
				admin.setEmail(adminEmail);
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123")); // Change this password
				admin.setRole(Role.ADMIN);
				userRepository.save(admin);
				System.out.println("✅ Admin user created: " + adminEmail);
			}
		};
	}

}
