package name.saifmahmud.demo.http.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;
}
