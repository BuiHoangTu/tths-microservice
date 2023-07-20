package bhtu.work.tths.authservice.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Document
@Data
public class User {
    @Id
    private String id;
    @NotBlank
    @Size(max = 50)
    private String username;
    @Size(min = 8, max = 50)
    private String password;
    @DBRef
    private Set<UserAcess> accesses = new HashSet<>();
    /**
     * City name, District name or Household Number for parents
     */
    private String accessRegion;

    public User(
            String id,
            @NotBlank @Size(max = 50) String username,
            @Size(min = 8, max = 50) String password,
            String accessRegion) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accessRegion = accessRegion;
    }

    
}
