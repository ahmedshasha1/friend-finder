package com.project.friend.dao.auth;

import com.project.friend.model.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Role getByRole(String role);
}
