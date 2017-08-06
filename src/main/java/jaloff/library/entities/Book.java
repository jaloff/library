package jaloff.library.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Books")
public class Book {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String title;

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

	public Book() {
	}

	public Book(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
}
