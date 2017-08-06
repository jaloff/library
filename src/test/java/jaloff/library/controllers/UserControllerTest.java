package jaloff.library.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import jaloff.library.config.SecurityConfig;
import jaloff.library.entities.User;
import jaloff.library.repositories.UserRepository;
import jaloff.library.utils.WithMockAdmin;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository usersRepo;

	@Autowired
	private ObjectMapper objMapper;
	
	private List<User> users;
	private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private static final long ID = 1L;
	private static final User USER_1 = new User(ID, "Bob", "Ross", "email@email.com", "password");
	private static final User USER_2 = new User(2L, "Alice", "Duke", "alice@email.com", "password");
	private static final User NOT_VALID_USER = new User(1L, "", null, "", ""); 
	
	@Before
	public void setUp() throws Exception {
		users = new ArrayList<>();
		users.add(USER_1);
		users.add(USER_2);
	}

	@Test
	@WithMockAdmin
	public void shouldReturnUserListForAdmin() throws Exception {
		when(usersRepo.findAll()).thenReturn(users);
		RequestBuilder rb = get("/users");
		
		mockMvc.perform(rb).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(USER_1.getId().intValue())))
			.andExpect(jsonPath("$[0].firstName", is(USER_1.getFirstName())))
			.andExpect(jsonPath("$[0].lastName", is(USER_1.getLastName())))
			.andExpect(jsonPath("$[0].email", is(USER_1.getEmail())))
			.andExpect(jsonPath("$[0].password").doesNotExist())
			.andExpect(jsonPath("$[1].id", is(USER_2.getId().intValue())))
			.andExpect(jsonPath("$[1].firstName", is(USER_2.getFirstName())))
			.andExpect(jsonPath("$[1].lastName", is(USER_2.getLastName())))
			.andExpect(jsonPath("$[1].email", is(USER_2.getEmail())))
			.andExpect(jsonPath("$[1].password").doesNotExist());
		
		verify(usersRepo, times(1)).findAll();
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotReturnUserListForUser() throws Exception {
		RequestBuilder rb = get("/users");
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(usersRepo);
	}

	@Test
	@WithMockAdmin
	public void shouldReturnUserByIdForAdmin() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.of(USER_1));
		RequestBuilder rb = get("/users/{id}", ID);

		mockMvc.perform(rb).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is((int)ID)))
			.andExpect(jsonPath("$.firstName", is(USER_1.getFirstName())))
			.andExpect(jsonPath("$.lastName", is(USER_1.getLastName())))
			.andExpect(jsonPath("$.email", is(USER_1.getEmail())))
			.andExpect(jsonPath("$.password").doesNotExist());
		
		verify(usersRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(usersRepo);
	}

	@Test
	@WithMockAdmin
	public void shouldReturn404IfUserNotExist() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.empty());
		RequestBuilder rb = get("/users/{id}", ID);

		mockMvc.perform(rb).andExpect(status().isNotFound());

		verify(usersRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotReturnUserByIdForUser() throws Exception {
		RequestBuilder rb = get("/users/{id}", ID);

		mockMvc.perform(rb).andExpect(status().isForbidden());

		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToRegisterNewUser() throws Exception {
		when(usersRepo.save(Mockito.any(User.class))).thenReturn(USER_1);
		when(usersRepo.existsByEmail(USER_1.getEmail())).thenReturn(false);
		RequestBuilder rb = post("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE);

		mockMvc.perform(rb).andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is((int)ID)))
			.andExpect(jsonPath("$.firstName", is(USER_1.getFirstName())))
			.andExpect(jsonPath("$.lastName", is(USER_1.getLastName())))
			.andExpect(jsonPath("$.email", is(USER_1.getEmail())))
			.andExpect(jsonPath("$.password").doesNotExist());

		verify(usersRepo, times(1)).existsByEmail(USER_1.getEmail());
		verify(usersRepo, times(1)).save(Mockito.any(User.class));
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldReturn409WhenRegisteringIfUserWithEmailAlreadyExist() throws Exception {
		when(usersRepo.existsByEmail(USER_1.getEmail())).thenReturn(true);
		RequestBuilder rb = post("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE);

		mockMvc.perform(rb).andExpect(status().isConflict());

		verify(usersRepo, times(1)).existsByEmail(USER_1.getEmail());
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldNotAllowUserToRegisterNewUser() throws Exception {
		RequestBuilder rb = post("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE);

		mockMvc.perform(rb).andExpect(status().isForbidden());

		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToDeleteUser() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.of(USER_1));
		doNothing().when(usersRepo).delete(USER_1);
		RequestBuilder rb = delete("/users/{id}", ID);
		
		mockMvc.perform(rb).andExpect(status().isOk());
		
		verify(usersRepo, times(1)).findById(ID);
		verify(usersRepo, times(1)).delete(USER_1);
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldReturn404IfUserToDeleteNotExist() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.empty());
		RequestBuilder rb = delete("/users/{id}", ID);
		
		mockMvc.perform(rb).andExpect(status().isNotFound());
		
		verify(usersRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldReturnNotAllowUserToDeleteUser() throws Exception {
		RequestBuilder rb = delete("/users/{id}", ID);
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldAllowAdminToUpdateUser() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.of(USER_1));
		when(usersRepo.save(Mockito.any(User.class))).thenReturn(USER_1);
		RequestBuilder rb = put("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isOk());
		
		verify(usersRepo, times(1)).findById(ID);
		verify(usersRepo, times(1)).save(Mockito.any(User.class));
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldReturn404IfUserToUpdateNotExist() throws Exception {
		when(usersRepo.findById(ID)).thenReturn(Optional.empty());
		RequestBuilder rb = put("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isNotFound());
		
		verify(usersRepo, times(1)).findById(ID);
		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockUser
	public void shouldReturnNotAllowUserToUpdateUser() throws Exception {
		RequestBuilder rb = put("/users")
				.content(objMapper.writeValueAsString(USER_1))
				.contentType(MEDIA_TYPE);
		
		mockMvc.perform(rb).andExpect(status().isForbidden());
		
		verifyNoMoreInteractions(usersRepo);
	}

	@Test
	@WithMockAdmin
	public void shouldReturn409WhenTryingToRegisterUserWithInvalidData() throws Exception {
		RequestBuilder rb = post("/users")
				.content(objMapper.writeValueAsString(NOT_VALID_USER))
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE);

		mockMvc.perform(rb).andExpect(status().isConflict());

		verifyNoMoreInteractions(usersRepo);
	}
	
	@Test
	@WithMockAdmin
	public void shouldReturn409WhenTryingToUpdateUserWithInvalidData() throws Exception {
		RequestBuilder rb = put("/users")
				.content(objMapper.writeValueAsString(NOT_VALID_USER))
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE);

		mockMvc.perform(rb).andExpect(status().isConflict());

		verifyNoMoreInteractions(usersRepo);
	}
}
