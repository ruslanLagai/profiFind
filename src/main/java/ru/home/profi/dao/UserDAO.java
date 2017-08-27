package ru.home.profi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.profi.entity.Profile;

public interface UserDAO extends JpaRepository<Profile, Long> {

    Profile save(Profile profile);
    Profile findByUsername(String username);

}
