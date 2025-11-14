package az.company.usermanagementservice.domain.dto.response;

import az.company.usermanagementservice.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String name;

    private String email;

    private String phone;

    private Role role;
}
