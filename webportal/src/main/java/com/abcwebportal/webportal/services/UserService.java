package com.abcwebportal.webportal.services;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;
/**
 * Interface to handle User related services.
 *
 */

@Service
public interface UserService {

	User findUserByUserName(String userName);

	User save(@Valid User user) throws WebPortalRuntimeException;

	boolean findByLogin(String userName, String password) throws WebPortalRuntimeException;

	User getByUserId(Long id) throws WebPortalRuntimeException;
}
