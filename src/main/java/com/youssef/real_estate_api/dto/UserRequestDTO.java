package com.youssef.real_estate_api.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Role is required")
    private String role;

    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Phone is required") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone is required") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Role is required") String getRole() {
        return role;
    }

    public void setRole(@NotBlank(message = "Role is required") String role) {
        this.role = role;
    }


}