package training.spring.monolithic.service;

import training.spring.monolithic.dto.UserDTO;
import training.spring.monolithic.entity.Users;
import training.spring.monolithic.exception.MonolithicException;

import java.util.List;

public interface UserService {
    List<Users> getAllUser();

    Users saveUser(Users user);

    boolean deleteUser(Long id);

    UserDTO findUserById(Long id);

    UserDTO addUser(UserDTO userDTO) throws MonolithicException;
}
