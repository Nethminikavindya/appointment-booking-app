package com.nethmini.controller;

import com.nethmini.modal.User;
import service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
      User createdUser = userService.createUser(user);
      return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        List <User> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
//        //create new user
//        User user = new User();
//        user.setFullName("Nethmini Kavindya");
//        user.setEmail("kavindya@gmail.com");
//        user.setPhone("+94 123456789");
//        user.setRole("Customer");
//        return user;

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws Exception {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                           @PathVariable Long id) throws Exception {
        User updateUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted",HttpStatus.ACCEPTED);}
}
