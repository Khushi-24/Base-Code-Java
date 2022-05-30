package com.demo.BaristaBucks.Entity;


import com.demo.BaristaBucks.Common.Entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Where(clause = "deleted_date IS NULL")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String userName;

    @Column
    private String profilePicturePath;

    @Column
    private String password;

    @Column
    private String countryCode;

    @Column
    private String phoneNo;

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private Boolean isSuspend = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "role_id", insertable = false, updatable = false, nullable = false)
    private Long roleId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("Admin");
        SimpleGrantedAuthority authority2 = new SimpleGrantedAuthority("User");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        authorities.add(authority2);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
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
