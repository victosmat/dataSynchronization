package com.sync.dataSynchronization.domain;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SERVER_FTP_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerFTPInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(length = 15, unique = true, nullable = false)
    private String ip;

    @NotNull
    @Column(length = 60, nullable = false)
    private String username;

    @NotNull
    @Column(length = 60, nullable = false)
    private String password;
}
