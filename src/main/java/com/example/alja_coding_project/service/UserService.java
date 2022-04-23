package com.example.alja_coding_project.service;

import com.example.alja_coding_project.entity.DbUser;
import com.example.alja_coding_project.payload.UserDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.repository.UserRepository;
import com.example.alja_coding_project.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<DbUser> getUsers() {
        return userRepository.findAll();
    }

    public DbUser getUser(Long id) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        return optionalDbUser.orElse(null);
    }

    public ApiResponse postUserRegister(UserDto userDto) {
        if (!userRepository.existsByEmail(userDto.getEmail())) {
            return saveInformation(new DbUser(), userDto, "Saved user information");
        } else {
            return new ApiResponse("The email you entered is busy", null, false);
        }
    }

    public ApiResponse postUserLogin(UserDto userDto) {
        Optional<DbUser> optionalDbUser = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        if (optionalDbUser.isPresent()) {
            DbUser user = generationUserRole(optionalDbUser.get());
            return new ApiResponse("User fund", user, true);
        } else {
            return new ApiResponse("User not found", null, false);
        }
    }

    public ApiResponse postAdmin(UserDto userDto) {
        Optional<DbUser> optionalDbUser = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        DbUser user;
        user = optionalDbUser.orElseGet(DbUser::new);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return new ApiResponse("Admin added", user, true);
    }

    public ApiResponse updateUser(Long id, UserDto userDto) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        if (optionalDbUser.isPresent()) {
            if (!userRepository.existsByEmailAndIdNot(userDto.getEmail(), id)) {
                return saveInformation(optionalDbUser.get(), userDto, "Update user information");
            } else {
                return new ApiResponse("The email you entered is busy", null, false);
            }
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }

    public ApiResponse deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
                return new ApiResponse("Delete user information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("The data was not deleted due to errors", null, false);
            }
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }

    private ApiResponse saveInformation(DbUser user, UserDto userDto, String text) {
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse(text, user, true);
    }

    private DbUser generationUserRole(DbUser user){
        if (user.getEmail().equals("dadayevjahongir1@gmail.com") && user.getPassword().equals("0987654321")){
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
        return user;
    }
}
