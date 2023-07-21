package bhtu.work;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckAccesses {
    public static boolean checkHeaders(HttpServletRequest request, String... accessible) {
        List<String> accesses = Collections.list(request.getHeaders("Accesses"));

        return accesses.stream().anyMatch((access) -> Arrays.asList(accessible).contains(access));
    }
}
