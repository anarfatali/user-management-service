package az.company.usermanagementservice.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
    private String phone;
}
