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

import com.mahesh.stocksimapp.dto.UserPortfolioPostDto;
import com.mahesh.stocksimapp.dto.UserPostDto;
import com.mahesh.stocksimapp.mappers.MapStructMapper;
import com.mahesh.stocksimapp.models.User;
import com.mahesh.stocksimapp.services.UserService;

import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS_BATCH;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS_BY_ID;
import static com.mahesh.stocksimapp.configs.Constants.ENDPOINT_V1_USERS_BUDGET_BY_ID;

import java.util.List;

@RestController
@RequestMapping(path = ENDPOINT_V1_USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    public UserController(
            MapStructMapper mapstructMapper,
            UserService userService
    ) {
        this.mapstructMapper = mapstructMapper;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Void> addUser(@RequestBody UserPostDto userPostDto) {
        userService.addOrUpdateUser(
            mapstructMapper.userPostDtoToUser(userPostDto)
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = ENDPOINT_V1_USERS_BATCH)
    public ResponseEntity<Void> addUsers(@RequestBody List<UserPortfolioPostDto> userPortfolioPostDto) {
        userService.addUsers(
            mapstructMapper.userPortfoliosPostDtoToUsers(userPortfolioPostDto)
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<UserPostDto> updateUser(@PathVariable(value = "id") String userId,
                                                   @RequestBody UserPostDto userUpdates) {
        userService.findById(userId);
        userService.addOrUpdateUser(
            mapstructMapper.userPostDtoToUser(userUpdates)
        );
        return ResponseEntity.ok(userUpdates);

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
