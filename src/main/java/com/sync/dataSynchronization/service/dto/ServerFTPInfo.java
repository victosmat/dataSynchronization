package com.sync.dataSynchronization.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServerFTPInfo {
    private String ip;
    private String username;
    private String password;
    private String pathFile;
}
