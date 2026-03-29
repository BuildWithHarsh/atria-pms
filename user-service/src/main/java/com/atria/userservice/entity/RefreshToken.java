package com.atria.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor @Entity @Table(name = "refresh_tokens", indexes = {
        @Index(name = "refresh_tokens_jti_idx", columnList = "jti", unique = true),
        @Index(name = "refresh_tokens_user_id_idx", columnList = "user_id")
})
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "jti", unique = true, nullable = false, updatable = false)
    private String jti;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    @Column(nullable = false)
    private boolean revoked;
    private String createAt;
    private String expiresAt;
    private String replacedByToken;

}
