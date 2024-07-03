package com.sync.dataSynchronization.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

import lombok.*;

import javax.validation.constraints.NotNull;


@Entity
@Table(name = "USER_AUTHENTICATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthenticate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(length = 50, unique = true, nullable = false)
    private String userHashCode;

    @JsonIgnore
    @NotNull
    @Column(length = 60, nullable = false)
    private String password;

    @NotNull
    @Column
    private Date createdDate;


}

