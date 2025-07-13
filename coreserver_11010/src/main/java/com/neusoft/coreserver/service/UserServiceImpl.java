package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.User;
import com.neusoft.coreserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public int deleteUser(int userId) {
        return 0;
    }

    @Transactional
    @Override
    public int updateUser(User user) {
        return 0;
    }


}
