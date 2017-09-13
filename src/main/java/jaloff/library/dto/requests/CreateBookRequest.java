package jaloff.library.dto.requests;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
	
	@NotNull
	@NotBlank
	private String title;
}
