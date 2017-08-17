package jaloff.library.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Books")
public class Book {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@NotBlank
	private String title;
	
	@NotNull
	private Status status;

	public Book(Long id, String title) {
		this.id = id;
		this.title = title;
		this.status = Status.AVAIABLE;
	}
	
	public enum Status {
		BORROWED, AVAIABLE
	}
}
