package dev.olena.wishapp.user;

import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;

import dev.olena.wishapp.profile.Profile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String userIdentifier;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getRealUsername() {
        return this.username;
    }

    @PrePersist
    private void generateUserIdentifier() {
        this.userIdentifier = generateShortUUID();
    }

    private String generateShortUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
