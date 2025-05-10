package com.nexapay.nexapay_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column
    private Integer id;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column String password;
}
