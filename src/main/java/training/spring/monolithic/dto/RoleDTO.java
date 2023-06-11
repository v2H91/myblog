package training.spring.monolithic.dto;

import training.spring.monolithic.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
	private int id;
	private String name;
	public RoleDTO(Role role){
		this.id = role.getRoleId();
		this.name = role.getName();
	}
}
