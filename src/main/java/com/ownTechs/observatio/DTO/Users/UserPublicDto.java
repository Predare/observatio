package com.ownTechs.observatio.DTO.Users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPublicDto {

    private Integer id;

    private String firstName;
    private String lastName;
    private String middleName;   
}

