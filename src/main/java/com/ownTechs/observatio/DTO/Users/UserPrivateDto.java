package com.ownTechs.observatio.DTO.Users;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPrivateDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @Builder.Default
    private boolean enabled = true;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String userGroupName;
}
