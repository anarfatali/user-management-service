package az.company.usermanagementservice.specification;

import lombok.Data;

@Data
public class UserFilter {

    private String name;
    private String email;
    private String phone;
}
