package com.ownTechs.observatio.DTO.Users;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserReqistryDto {


    private String username;
    private String password;
    private String repassword;   
    @Builder.Default
    private boolean enabled = true;
        
    }
    
