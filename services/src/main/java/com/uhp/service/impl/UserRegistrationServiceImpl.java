package com.uhp.service.impl;

import com.uhp.dto.UserDTO;
import com.uhp.entity.User;
import com.uhp.repository.UserResourceRepository;
import com.uhp.service.UserRegistrationService;
import com.uhp.serviceobject.UserRegistrationInfo;
import com.uhp.util.HashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Bogdan Kovalev.
 */
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserResourceRepository userResourceRepository;

    @Override
    public UserDTO register(UserRegistrationInfo registrationInfo) throws Exception {
        final byte[] salt = HashingUtil.randomSalt();
        final String passwordHash = HashingUtil.createHash(registrationInfo.password, salt);
        final User newUser = new User(registrationInfo.name.getValue(), registrationInfo.email.getValue(), passwordHash, salt);
        this.userResourceRepository.create(newUser);
        return new UserDTO(newUser.getId(), newUser.getName(), newUser.getEmail());
    }

}
