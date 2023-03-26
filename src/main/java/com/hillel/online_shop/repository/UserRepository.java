package com.hillel.online_shop.repository;

import com.hillel.online_shop.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
