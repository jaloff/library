package jaloff.library.dto.requests;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnBookRequest {

	@NotNull
	private Long bookId;
}
