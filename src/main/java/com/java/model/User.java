package com.java.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String profileInfo;

    @Column(name = "kyc_status")
    private String kycStatus;

    @Column(name = "mfa_enabled", nullable = false)
    private boolean mfaEnabled = false;

    @Column(name = "mfa_secret")
    private String mfaSecret;

    @Type(type = "jsonb")
    @Column(name = "mfa_backup_codes", columnDefinition = "jsonb")
    private String mfaBackupCodes;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_id")
    private String oauthId;
}
