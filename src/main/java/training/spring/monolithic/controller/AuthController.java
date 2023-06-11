package training.spring.monolithic.controller;

import training.spring.monolithic.dto.LoginDetail;
import training.spring.monolithic.entity.APIResponse;
import training.spring.monolithic.service.impl.AuthenticationServiceImpl;
import training.spring.monolithic.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationServiceImpl authenticationService;
    private final JWTUtils jwtTokenUtil;

    @PostMapping("authenticate")
    public APIResponse<String> authenticate(@RequestBody LoginDetail loginDetail) throws Exception {
        authenticate(loginDetail.getUserName(), loginDetail.getPassword());
        final UserDetails userDetails = authenticationService.loadUserByUsername(loginDetail.getUserName());
        final String token = jwtTokenUtil.generateToken(userDetails);
        APIResponse<String> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Authenticate successful");
        responseData.setData(token);
        return responseData;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("logout")
    public APIResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        APIResponse<String> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Logout successful");
        responseData.setData("Logout successful");
        return responseData;
    }
}
