package ru.home.profi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.home.profi.dao.UserDAO;
import ru.home.profi.entity.Profile;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void save(Profile profile) {
        userDAO.save(profile);
    }

    public Profile findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
