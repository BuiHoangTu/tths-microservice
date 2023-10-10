package bhtu.work.tths.authservice.models;

import bhtu.work.tths.share.models.enums.EUserAccess;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
public class UserAccess {
    @Id
    private int id;
    private EUserAccess access;

    public UserAccess(EUserAccess access) {
        this.access = access;
        this.id = access.value;
    }

    public static Set<UserAccess> build (EUserAccess...eUserAccesses) {
        var s = new HashSet<UserAccess>(eUserAccesses.length);
        for(var e : eUserAccesses) {
            var a = new UserAccess(e);
            s.add(a);
        }
        return s;
    }

}
