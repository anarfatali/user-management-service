package az.company.usermanagementservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeletedEvent {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime deletedAt;
}