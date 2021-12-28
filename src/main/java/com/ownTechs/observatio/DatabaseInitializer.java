package com.ownTechs.observatio;

import com.ownTechs.observatio.DTO.Users.UserReqistryDto;
import com.ownTechs.observatio.Service.Interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


@Data
@AllArgsConstructor
@Component
public class DatabaseInitializer<UserGroupService> implements ApplicationListener<ApplicationReadyEvent> {

    private final UserGroupService userGroupService;

    private final List<String> usernames = List.of(
            "Predare"
    );

    private final String password = "503553";

    private final UserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        List<UserReqistryDto> newUsers = new LinkedList<UserReqistryDto>();

        for (int i = 0; i < usernames.size(); ++i) {
            UserReqistryDto request = 
                UserReqistryDto.builder()
            .username(usernames.get(i))
            .password(password)
            .repassword(password)
            .build();

            newUsers.add(request);
            
        }
        try {
            userService.create(newUsers);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}