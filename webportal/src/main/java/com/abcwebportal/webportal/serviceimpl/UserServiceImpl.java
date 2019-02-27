package com.abcwebportal.webportal.serviceimpl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.abcwebportal.webportal.base.MessageCode;
import com.abcwebportal.webportal.daoservice.UserDaoService;
import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;
import com.abcwebportal.webportal.services.UserService;

/**
 * Implements {@link UserService}
 */
@Component
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDaoService daoService;

	/**
	 * <p>
	 * will try to find the user with given username .
	 * </p>
	 * 
	 * @param userName
	 * @return {@link User}
	 */
	@Override
	public User findUserByUserName(String userName) {
		User user = daoService.findUserByUserName(userName);
		if (user == null)
			throw new WebPortalRuntimeException(MessageCode.PORATG001UserNotFound);
		else
			return user;
	}

	/**
	 * <p>
	 * will try persist the user object by calling {@link UserDaoService} save
	 * method
	 * </p>
	 * 
	 * @param userName
	 * @return {@link User} - persisted
	 */
	@Override
	public User save(@Valid User user) {
		daoService.save(user);
		return findUserByUserName(user.getUsername());
	}

	/**
	 * <p>
	 * will try to find the user with given username and password
	 * </p>
	 * 
	 * @param password
	 * @return boolean - true if found
	 * @throws WebPortalRuntimeException
	 *             if not found
	 */
	@Override
	public boolean findByLogin(String username, String password) {
		try {
			daoService.findUserByUserName(username);
			return true;
		} catch (WebPortalRuntimeException wEx) {
			throw new WebPortalRuntimeException(MessageCode.PORATG001UserNotFound);
		}
	}

	@Override
	public User getByUserId(Long id) {
		return daoService.getByUserId(id);
	}

}
