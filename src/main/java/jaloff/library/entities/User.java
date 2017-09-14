package jaloff.library.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@NotBlank
	private String firstName;
	
	@NotNull
	@NotBlank
	private String lastName;
	
	@NotNull
	@NotBlank
	private String email;
	
	@JsonIgnore
	private String password;
	
	private String role;
	
	@JsonIgnore
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Issue> issues;

	@JsonIgnore
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Return> returns;
	
	public User(Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = Role.ROLE_USER.toString();
	}
}
