package com.mahesh.stocksimapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahesh.stocksimapp.models.User;
import com.mahesh.stocksimapp.services.UserService;

import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS_BY_ID;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS_BUDGET_BY_ID;

import java.util.List;

@RestController
@RequestMapping(path = ENDPOINT_V1_USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public User addUser(@RequestBody User user) {
        return userService.addOrUpdateUser(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping(path = ENDPOINT_V1_USERS_BY_ID)
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") String userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok().body(user);
    }

    // @GetMapping(path = ENDPOINT_V1_USERS_BUDGET_BY_ID)
    // public ResponseEntity<Float> findBudgetById(@PathVariable(value = "id") Integer userId) {
    //     Float buyingPower = userService.findBudgetById(userId);
    //     return ResponseEntity.ok().body(buyingPower);
    // }

    @PutMapping(path = ENDPOINT_V1_USERS_BY_ID)
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") String userId,
                                                   @RequestBody User userUpdates) {
        User user = userService.findById(userId);
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
        user.setEmailId(userUpdates.getEmailId());
        user.setBuyingPower(userUpdates.getBuyingPower());
        final User updatedUser = userService.addOrUpdateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

    @PutMapping(path = ENDPOINT_V1_USERS_BUDGET_BY_ID)
    public ResponseEntity<User> updateBudgetOfUser(@PathVariable(value = "id") String userId,
                                                   @RequestBody User userUpdates) {
        User user = userService.findById(userId);
        user.setBuyingPower(userUpdates.getBuyingPower());
        final User updatedUser = userService.addOrUpdateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			userService.deleteAllUsers();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @DeleteMapping(path = ENDPOINT_V1_USERS_BY_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") String userId) {
        User user = userService.findById(userId);
        userService.deleteUser(user);
        return ResponseEntity.ok().build();
    }
}
