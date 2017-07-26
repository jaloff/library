package jaloff.library.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import jaloff.library.entities.Book;
import jaloff.library.repositories.BooksRepository;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BooksController.class, secure = false)
public class BooksControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BooksRepository booksRepo;

	private List<Book> books;
	private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private static final Book BOOK_1 = new Book(1L, "Diune");
	private static final Book BOOK_2 = new Book(1L, "American Assassin");
	
	@Before
	public void setUp() throws Exception {
		books = new ArrayList<>();
		books.add(BOOK_1);
		books.add(BOOK_2);
	}

	@Test
	public void shouldReturnBooks() throws Exception {
		when(booksRepo.findAll()).thenReturn(books);
		RequestBuilder rb = get("/books")
				.accept(MEDIA_TYPE);
		mockMvc.perform(rb)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MEDIA_TYPE))
			.andExpect(jsonPath("$", hasSize(books.size())))
			.andExpect(jsonPath("$[0].id", is((int)books.get(0).getId())))
			.andExpect(jsonPath("$[0].title", is(books.get(0).getTitle())))
			.andExpect(jsonPath("$[1].id", is((int)books.get(1).getId())))
			.andExpect(jsonPath("$[1].title", is(books.get(1).getTitle())));
		
		verify(booksRepo, times(1)).findAll();
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	public void shouldReturnBookById() throws Exception {
		int id = (int)BOOK_1.getId();
		String title = BOOK_1.getTitle();
		when(booksRepo.findById(id)).thenReturn(Optional.of(BOOK_1));
		RequestBuilder rb = get("/books/{id}", id)
					.accept(MEDIA_TYPE);
		mockMvc.perform(rb).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(id)))
			.andExpect(jsonPath("$.title", is(title)));
		
		verify(booksRepo, times(1)).findById(id);
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	public void shouldReturn404CodeWhenNotFoundById() throws Exception {
		long id = 0L;
		when(booksRepo.findById(id)).thenReturn(Optional.empty());
		RequestBuilder rb = get("/books/{id}", id)
			.accept(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isNotFound());
		
		verify(booksRepo, times(1)).findById(id);
		verifyNoMoreInteractions(booksRepo);
	}
}
