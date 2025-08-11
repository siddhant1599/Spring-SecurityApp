package com.springsecurity.SecurityApp.SecurityApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;

    @ManyToOne
    private User user;

}
