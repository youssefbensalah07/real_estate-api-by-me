package com.youssef.real_estate_api.dto;


import com.youssef.real_estate_api.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Role is required")
    private Role role;

    public void setRole(@NotBlank(message = "Role is required") Role role) {
        this.role = role;
   }

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




}