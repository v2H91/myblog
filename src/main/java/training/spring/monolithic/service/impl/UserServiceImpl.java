package training.spring.monolithic.service.impl;

import training.spring.monolithic.dto.UserDTO;
import training.spring.monolithic.entity.Role;
import training.spring.monolithic.entity.Users;
import training.spring.monolithic.exception.MonolithicException;
import training.spring.monolithic.repository.RoleRepository;
import training.spring.monolithic.repository.UserRepository;
import training.spring.monolithic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public final BCryptPasswordEncoder encoder;

    @Override
    public List<Users> getAllUser() {
        return (List<Users>) userRepository.findAll();
    }

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);

    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO findUserById(Long id) {
        Optional<Users> users = userRepository.findById(id);
        return users.map(UserDTO::new).orElseGet(UserDTO::new);
    }

    public UserDTO addUser(UserDTO userDTO) throws MonolithicException {
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        Users users = new Users(userDTO);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        users.setStatus(1);
        users.setRoles(roles);
        try {
            Users response = userRepository.save(users);
            return new UserDTO(response);
        } catch (Exception e) {
            throw new MonolithicException("User's information is invalid.", e.getCause());
        }
    }

    public boolean isUsernameExist(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    public boolean isEmailExits(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
