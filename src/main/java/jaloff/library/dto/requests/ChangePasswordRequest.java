package jaloff.library.dto.requests;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
	
	@NotNull
	@NotBlank
	private String oldPassword;
	
	@NotNull
	@NotBlank
	private String newPassword;
}
