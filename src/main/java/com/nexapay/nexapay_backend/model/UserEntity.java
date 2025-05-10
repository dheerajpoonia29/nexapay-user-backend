package com.nexapay.nexapay_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column
    private Integer id;

    @Column
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;
}
