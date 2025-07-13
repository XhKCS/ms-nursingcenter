package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.User;

public interface UserService {
    int deleteUser(int userId);

    int updateUser(User user);

}
