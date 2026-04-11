package gfg.user;


import gfg.user.dto.UserDto;
import gfg.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public long createUser(@RequestBody UserDto userDto) throws ExecutionException, InterruptedException {
        return userService.createUser(userDto);
    }



    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable Long id) throws ExecutionException, InterruptedException {
        return userService.getUserDetails(id);
    }



}
