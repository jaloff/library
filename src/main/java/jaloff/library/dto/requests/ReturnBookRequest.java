package jaloff.library.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnBookRequestDto {

	@NotNull
	private Long bookId;
}
