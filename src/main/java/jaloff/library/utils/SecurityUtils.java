package jaloff.library.utils;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jaloff.library.entities.Role;
import jaloff.library.entities.User;

public class SecurityUtils {

	public static boolean isAdminOrOwner(User user) throws AccessDeniedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		
		boolean isAdmin = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(s -> s.compareTo(Role.ROLE_ADMIN.toString()) == 0);

		if(isAdmin || email.compareTo(user.getEmail()) == 0) {
			return true;
		}
		
		throw new AccessDeniedException("");
	}
}
