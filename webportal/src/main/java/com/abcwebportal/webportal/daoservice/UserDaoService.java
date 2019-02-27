package com.abcwebportal.webportal.daoservice;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.abcwebportal.webportal.exception.WebPortalRuntimeException;
import com.abcwebportal.webportal.model.user.User;

/**
 * Interface to handle User persistence level services.
 *
 */

@Service
public interface UserDaoService {

	User findUserByUserName(String username) throws WebPortalRuntimeException;

	User save(@Valid User user) throws WebPortalRuntimeException;

	User getByUserId(Long id) throws WebPortalRuntimeException;
}
