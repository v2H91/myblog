package training.spring.monolithic.controller;

import training.spring.monolithic.dto.UserDTO;
import training.spring.monolithic.entity.APIResponse;
import training.spring.monolithic.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public APIResponse<UserDTO> getById(@PathVariable Long id) {
        UserDTO users = userService.findUserById(id);
        APIResponse<UserDTO> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Find by id user successful");
        responseData.setData(users);
        return responseData;
    }

    @PostMapping("/register")
    public APIResponse<UserDTO> registerNewUser(@RequestBody UserDTO userDTO) {
        if (userService.isEmailExits(userDTO.getEmail()) || userService.isUsernameExist(userDTO.getUserName())) {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Username or Email already exist", null);
        } else {
            userDTO.setStatus(1);
            try {

                UserDTO response = userService.addUser(userDTO);
                return new APIResponse<>(HttpStatus.OK.value(), "Register successful", response);
            }catch (Exception e){
                return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Register fail. " + e.getMessage(), null);
            }
        }

    }
}
