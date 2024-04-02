package com.nevin.sunny.service;

import com.nevin.sunny.dao.IUserDao;
import org.springframework.stereotype.Service;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:35 pm
 */
@Service
public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void initliseUserData(){
        userDao.initializeUserData();
    }
}
