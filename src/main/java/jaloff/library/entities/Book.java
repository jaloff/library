package jaloff.library.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Book() {
	}

	public Book(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
		this.status = Status.AVAIABLE;
	}
	
	public enum Status {
		BORROWED, AVAIABLE
	}
}
