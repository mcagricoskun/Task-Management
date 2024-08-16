package com.btk.bsd.controller;

import com.btk.bsd.dto.UserDTO;
import com.btk.bsd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(required = true) Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET ALL
    @GetMapping("/get-users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDto = userService.getAllUsers();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("create-user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto){
        UserDTO createdUser  = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/update-user")
    public ResponseEntity<UserDTO> updateUser(@RequestParam Long id , @RequestBody UserDTO userDTO){
        UserDTO updatedUser = userService.updateUser(id,userDTO);
        if(updatedUser != null){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    }

    // DELETE
    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestBody Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
