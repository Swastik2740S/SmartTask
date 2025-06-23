package com.smarttask.dto;

import java.io.Serializable;

// DTO for returning JWT token after authentication
public class AuthResponse implements Serializable {
    private String jwt;

    public AuthResponse() {
    }

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
