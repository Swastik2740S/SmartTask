package com.smarttask;

import com.smarttask.dto.ProjectResponseDTO;
import com.smarttask.dto.UserTeamResponseDTO;
import com.smarttask.model.Project;
import com.smarttask.model.UserTeam;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SmartTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartTaskApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		// Mapping for UserTeam -> UserTeamResponseDTO
		modelMapper.addMappings(new PropertyMap<UserTeam, UserTeamResponseDTO>() {
			@Override
			protected void configure() {
				map().setUserId(source.getUser().getUserId());
				map().setTeamId(source.getTeam().getTeamId());
				map().setRole(source.getRole());
			}
		});

		// Mapping for Project -> ProjectResponseDTO
		modelMapper.addMappings(new PropertyMap<Project, ProjectResponseDTO>() {
			@Override
			protected void configure() {
				map().setTeamId(source.getTeam().getTeamId());
			}
		});

		return modelMapper;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
