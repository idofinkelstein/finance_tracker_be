package com.ido.financetracker.auth.repository;

import com.ido.financetracker.auth.entity.User;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(@NonNull String username, @NonNull String email);

    Boolean existsByUsername(@NonNull String username);
    Boolean existsByEmail(@NonNull String email);

}
