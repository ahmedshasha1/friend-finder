package com.project.friend.model.auth;

import com.project.friend.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROLES")
public class Role extends BaseEntity {
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
