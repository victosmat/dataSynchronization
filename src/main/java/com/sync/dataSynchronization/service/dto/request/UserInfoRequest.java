package com.sync.dataSynchronization.service.dto.request;

import com.sync.dataSynchronization.domain.UserAuthenticate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String userHashCode;

    @NotBlank
    @Size(min = 1, max = 60)
    private String password;
}
