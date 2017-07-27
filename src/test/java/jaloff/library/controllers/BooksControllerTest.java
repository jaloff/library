package jaloff.library.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import jaloff.library.config.SecurityConfig;
import jaloff.library.entities.Book;
import jaloff.library.repositories.BooksRepository;
import jaloff.library.utils.WithMockAdmin;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BooksController.class)
@Import(SecurityConfig.class)
public class BooksControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BooksRepository booksRepo;

	@Autowired
	private ObjectMapper objMapper; 
	
	private List<Book> books;
	private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private static final Book BOOK_1 = new Book(1L, "Diune");
	private static final Book BOOK_2 = new Book(2L, "American Assassin");
	private static final Book BOOK_3 = new Book(3L, "Ender Game");
	private static final long ID = BOOK_1.getId();
	
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
		String title = BOOK_1.getTitle();
		when(booksRepo.findById(ID)).thenReturn(Optional.of(BOOK_1));
		RequestBuilder rb = get("/books/{id}", ID)
					.accept(MEDIA_TYPE);
		mockMvc.perform(rb).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is((int)ID)))
			.andExpect(jsonPath("$.title", is(title)));
		
		verify(booksRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	public void shouldReturn404WhenBookNotFoundById() throws Exception {
		when(booksRepo.findById(ID)).thenReturn(Optional.empty());
		RequestBuilder rb = get("/books/{id}", ID)
			.accept(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isNotFound());
		
		verify(booksRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToAddBook() throws Exception {
		when(booksRepo.save(Mockito.any(Book.class))).thenReturn(BOOK_3);
		RequestBuilder rb = post("/books")
				.accept(MEDIA_TYPE)
				.content(objMapper.writeValueAsString(BOOK_3))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is(BOOK_3.getTitle())));
		
		verify(booksRepo, times(1)).save(Mockito.any(Book.class));
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotAllowUserToAddBook() throws Exception {
		RequestBuilder rb = post("/books")
				.accept(MEDIA_TYPE)
				.content(objMapper.writeValueAsString(BOOK_3))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToDeleteBook() throws Exception {
		when(booksRepo.findById(ID)).thenReturn(Optional.of(BOOK_1));
		RequestBuilder rb = delete("/books/{id}", ID)
				.accept(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isOk());
		
		verify(booksRepo, times(1)).findById(ID);
		verify(booksRepo, times(1)).delete(ID);
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldReturn404WhenBookToDeleteNotExist() throws Exception {
		when(booksRepo.findById(ID)).thenReturn(Optional.empty());
		RequestBuilder rb = delete("/books/{id}", ID)
				.accept(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isNotFound());
		verify(booksRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotAllowUserToDeleteBook() throws Exception {
		RequestBuilder rb = delete("/books/{id}", ID)
				.accept(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToUpdateBook() throws Exception {
		when(booksRepo.save(Mockito.any(Book.class))).thenReturn(BOOK_1);
		when(booksRepo.findById(ID)).thenReturn(Optional.of(BOOK_1));
		RequestBuilder rb = put("/books")
				.accept(MEDIA_TYPE)
				.content(objMapper.writeValueAsString(BOOK_1))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isOk());
		
		verify(booksRepo, times(1)).findById(ID);
		verify(booksRepo, times(1)).save(Mockito.any(Book.class));
		verifyNoMoreInteractions(booksRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotAllowUserToUpdateBook() throws Exception {
		RequestBuilder rb = put("/books")
				.accept(MEDIA_TYPE)
				.content(objMapper.writeValueAsString(BOOK_1))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(booksRepo);
	}
}
