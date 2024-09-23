package com.refi.model;

import com.refi.model.documents.KycDocument;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

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

    @Column(columnDefinition = "jsonb")
    private String profileInfo;

    @Column(name = "kyc_status")
    private String kycStatus;

    @Column(name = "mfa_enabled", nullable = false)
    private boolean mfaEnabled = false;

    @Column(name = "mfa_secret")
    private String mfaSecret;

    @Column(name = "mfa_backup_codes", columnDefinition = "jsonb")
    private String mfaBackupCodes;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_id")
    private String oauthId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id") // This maps to the column in llc_document table
    private List<KycDocument> documents;
}
