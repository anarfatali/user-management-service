package az.company.usermanagementservice.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$",
            message = "Phone must be a valid number with 7 to 15 digits, optional country code")
    private String phone;
}
