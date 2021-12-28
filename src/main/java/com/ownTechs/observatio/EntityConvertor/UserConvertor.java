package com.ownTechs.observatio.EntityConvertor;

import com.ownTechs.observatio.DTO.Users.UserDefaultDetails;
import com.ownTechs.observatio.DTO.Users.UserPrivateDto;

import com.ownTechs.observatio.DTO.Users.UserPublicDto;
import com.ownTechs.observatio.DTO.Users.UserReqistryDto;
import com.ownTechs.observatio.Entity.User;
import com.ownTechs.observatio.Security.Role;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
@Component
public class UserConvertor {

    public UserPublicDto fromUserToUserPublicDto(User user) {
        return UserPublicDto.builder()
            .id(user.getId())
            .build();
    }

    public User fromUserPrivateDtoToUser(UserPrivateDto userPrivateDto) {
        
        return User.builder()
            .id(userPrivateDto.getId())
            .username(userPrivateDto.getUsername())
            .password(userPrivateDto.getPassword())
            .enabled(userPrivateDto.isEnabled())
            .createdAt(userPrivateDto.getCreatedAt())
            .modifiedAt(userPrivateDto.getModifiedAt())
            .build();
    }

    public UserPrivateDto fromUserToUserPrivateDto(User user) {
        
        return UserPrivateDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .enabled(user.getEnabled())
            .createdAt(user.getCreatedAt())
            .modifiedAt(user.getModifiedAt())
            .build();
    }

    public User fromUserRegistryDtoToUser(UserReqistryDto userReqistryDto)
    {
        return User.builder()
            .username(userReqistryDto.getUsername())
            .password(userReqistryDto.getPassword())
            .enabled(userReqistryDto.isEnabled())
            .build();
    }

    public UserDefaultDetails fromUserToUserDetails(User user) {
        return UserDefaultDetails.builder()
            .id(user.getId())
            .username(user.getUsername())
            .password(user.getPassword())
            .enabled(user.getEnabled())
            .authorities(new ArrayList<Role>())
            .build();
    }


}
