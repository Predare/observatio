package com.ownTechs.observatio.Service.Default;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.ownTechs.observatio.DTO.Users.UserPrivateDto;
import com.ownTechs.observatio.DTO.Users.UserPublicDto;
import com.ownTechs.observatio.DTO.Users.UserReqistryDto;
import com.ownTechs.observatio.Entity.User;
import com.ownTechs.observatio.EntityConvertor.UserConvertor;
import com.ownTechs.observatio.Repository.UserRepo;
import com.ownTechs.observatio.Service.Interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DefaultUserService implements UserService {

    private final UserRepo userRepo;
    private final UserConvertor userConvertor;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Boolean delete(List<Integer> userIds){
        
       // if(userIds == null) throw new ValidationException("User id's is null");

        for (Integer integer : userIds) {          
            userRepo.deleteById(integer);
        }

        return true;
    }

    @Override
    public List<UserPublicDto> findAll() {
        return userRepo.findAll().stream().map(x -> userConvertor.fromUserToUserPublicDto(x)).collect(Collectors.toList());
    }

    @Override
    public UserPublicDto findById(Integer id) {
        Optional<User> user = userRepo.findById(id);
        if(!user.isPresent()) return null;
        return userConvertor.fromUserToUserPublicDto(user.get());
    }

    @Override
    public UserPrivateDto findByUsername(String name) {
        Optional<User> user = userRepo.findByUsername(name);
        if(!user.isPresent()) return null;
        return userConvertor.fromUserToUserPrivateDto(user.get());
    }

    @Transactional
    public List<UserPublicDto> create(List<UserReqistryDto> request) throws Exception {
       
        List<UserPublicDto> responseList = new LinkedList<>();

        for (UserReqistryDto userReqistryDto : request) {
            if (userRepo.findByUsername(userReqistryDto.getUsername()).isPresent()) {
                throw new Exception("Username exists!");
            }
            if (!userReqistryDto.getPassword().equals(userReqistryDto.getRepassword())) {
                throw new Exception("Passwords don't match!");
            }

            User user = userConvertor.fromUserRegistryDtoToUser(userReqistryDto);
            user.setPassword(passwordEncoder.encode(userReqistryDto.getPassword()));
    
            user = userRepo.save(user);

            responseList.add(userConvertor.fromUserToUserPublicDto(user));
        }

        return responseList;
    }

    public List<UserPrivateDto> edit(List<UserPrivateDto> request) {
        for (UserPrivateDto userPrivateDto : request) {

            if(!userRepo.findById(userPrivateDto.getId()).isPresent()) continue;

            User user = userConvertor.fromUserPrivateDtoToUser(userPrivateDto);

            userRepo.save(user);
        }
        return null;
    }

    @Override
    public List<UserPrivateDto> get(@Valid List<Integer> request) {

        List<UserPrivateDto> foundedUsers = new LinkedList<>(); 

        for (Integer id : request) {

            Optional<User> user = userRepo.findById(id);

            if(!user.isPresent()) continue;

            foundedUsers.add(userConvertor.fromUserToUserPrivateDto(user.get()));
        }
        return foundedUsers;
    }       
}
