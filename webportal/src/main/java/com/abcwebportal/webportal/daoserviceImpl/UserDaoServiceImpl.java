package com.abcwebportal.webportal.daoserviceImpl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.abcwebportal.webportal.base.MessageCode;
import com.abcwebportal.webportal.daoservice.UserDaoService;
import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.repositories.UserRepository;

/**
 * 
 * Implements {@link UserDaoService}
 *
 */
@Component
public class UserDaoServiceImpl implements UserDaoService {

	@Autowired
	private UserRepository repository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findUserByUserName(String username) {
		User user = repository.findByUsername(username);
		return user;
	}

	/**
	 * 
	 * <p>
	 * Creates a new {@code User} object.
	 * </p>
	 * 
	 * @param user
	 *            - user object need to be persisted.
	 * @return User - User object persisted
	 * @throws WebPortalRuntimeException
	 *             if user already exist with given username.
	 */

	public User save(@Valid User user) {
		User userObj = null;
		userObj = findUserByUserName(user.getUsername());

		if (userObj == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			repository.save(user);
		} else {
			throw new WebPortalRuntimeException(MessageCode.PORTALG002UserExist);
		}
		return userObj;
	}

	/**
	 * <p>
	 * Get user {@code repository.getById(userId) }} object by userId.
	 * </p>
	 * 
	 * @Param userId - userId
	 * @return User - details object
	 * @throws WebPortalRuntimeException
	 *             PortalMessageCode.PORATG001UserNotFound
	 */
	@Override
	public User getByUserId(Long userId) {

		User userDetails = repository.getById(userId);
		if (userDetails == null) {
			throw new WebPortalRuntimeException(MessageCode.PORATG001UserNotFound);
		}
		return repository.getById(userId);

	}

}
