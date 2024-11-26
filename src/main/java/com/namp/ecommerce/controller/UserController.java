package com.namp.ecommerce.controller;

import com.namp.ecommerce.dto.UserDTO;
import com.namp.ecommerce.dto.UserEditableDTO;
import com.namp.ecommerce.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api-namp")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/admin/user")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok(userService.getUsers()); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());  //500
        }
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> getUserId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id)); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());  //500
        }
    }

    @PostMapping("/admin/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        try{
            UserDTO createdUserDTO = userService.save(userDTO);

            // Ya se encuentra registrado
            if (createdUserDTO == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("This user already exists");
            }

            return ResponseEntity.ok(createdUserDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the user:"+e.getMessage());
        }
    }

    @PutMapping("/admin/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @Valid @RequestBody UserEditableDTO userEditableDTO){
        try{
            UserEditableDTO updatedUser = userService.update(id, userEditableDTO);

            if (updatedUser == null){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("User not found");
            }
            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating the user: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        try{
            if (!userService.findById(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The user does not exist");
            }
            userService.delete(id);
            return ResponseEntity.ok().body("Deleted user");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the user: " + e.getMessage());
        }
    }
}
