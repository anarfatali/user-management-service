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
public class UserUpdatedEvent {

    private Long id;
    private String newName;
    private String newEmail;
    private String newPhone;
    private LocalDateTime updatedAt;
}