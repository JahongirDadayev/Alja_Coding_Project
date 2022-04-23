package com.example.alja_coding_project.controller;

import com.example.alja_coding_project.entity.DbUser;
import com.example.alja_coding_project.payload.UserDto;
import com.example.alja_coding_project.payload.result.ApiResponse;
import com.example.alja_coding_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<DbUser>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DbUser> getUser(@PathVariable Long id) {
        DbUser user = userService.getUser(id);
        return (user != null) ? ResponseEntity.status(HttpStatus.OK).body(user) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> postUserRegister(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.postUserRegister(userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> postUserLogin(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.postUserLogin(userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PostMapping(path = "/admin")
    public ResponseEntity<ApiResponse> postAdmin(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.postAdmin(userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @PathVariable Long id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.updateUser(id, userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
