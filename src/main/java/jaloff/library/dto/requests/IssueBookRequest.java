package jaloff.library.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueBookRequestDto {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Long bookId;
}
