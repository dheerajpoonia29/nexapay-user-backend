package com.nexapay.nexapay_backend.model;

import com.nexapay.nexapay_backend.dto.UserResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private AccountEntity accountEntity;

    public UserResponse toResponse() {
        return UserResponse.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .accountResponse(this.accountEntity !=null ? this.accountEntity.toResponse() : null)
                .build();
    }
}
