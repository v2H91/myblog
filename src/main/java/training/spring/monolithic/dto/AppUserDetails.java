package training.spring.monolithic.dto;

import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String username;
	private String fullName;
	@JsonIgnore
	private String password;
	private String jwtToken;
	private List<GrantedAuthority> authorities;

	public AppUserDetails(String username, String password, String fullName, String jwtToken, List<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.jwtToken = jwtToken;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
