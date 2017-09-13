package jaloff.library.dto.requests;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueBookRequest {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Long bookId;
}
