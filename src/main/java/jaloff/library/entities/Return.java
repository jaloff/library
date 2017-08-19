package jaloff.library.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Entity
@Table(name="Returns")
public class Return {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@NotNull
	private Date issueDate;
	
	@NotNull
	private Date returnDate;

}
