package com.ownTechs.observatio.Entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor

public class User implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }
}
