package training.spring.monolithic.service.impl;

import training.spring.monolithic.dto.AppUserDetails;
import training.spring.monolithic.dto.UserDTO;
import training.spring.monolithic.entity.Role;
import training.spring.monolithic.entity.Users;
import training.spring.monolithic.repository.RoleRepository;
import training.spring.monolithic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            Optional<Users> usersOptional = userRepository.findByUserName(username);
            UserDTO user = usersOptional.map(UserDTO::new).orElseThrow();
            List<Role> roles = roleRepository.findByUsers_UserName(username);
            List<GrantedAuthority> grantList = new ArrayList<>();
            if (!roles.isEmpty()) {
                roles.forEach(r -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority(r.getName());
                    grantList.add(authority);
                });
            }
            return new AppUserDetails(user.getUserName(), user.getPassword(), user.getFullName(), "", grantList);
        } catch (Exception e) {
            throw new UsernameNotFoundException(username + " not found!");
        }
    }
}
