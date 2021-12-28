package com.ownTechs.observatio.Service.Interfaces;

import com.ownTechs.observatio.DTO.Users.UserPrivateDto;
import com.ownTechs.observatio.DTO.Users.UserPublicDto;
import com.ownTechs.observatio.DTO.Users.UserReqistryDto;

import java.util.List;

import javax.validation.Valid;

public interface UserService {

    Boolean delete(List<Integer> userId);

    List<UserPublicDto> findAll();

    UserPublicDto findById(Integer id);

    List<UserPublicDto> create(List<UserReqistryDto> request) throws Exception;

    List<UserPrivateDto> edit(List<UserPrivateDto> request);

    List<UserPrivateDto> get(@Valid List<Integer> request);

    UserPrivateDto findByUsername(@Valid String request);
}
