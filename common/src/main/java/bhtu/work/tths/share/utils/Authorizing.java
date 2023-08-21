package bhtu.work.tths.share.utils;

import bhtu.work.tths.share.models.enums.EUserAccess;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Authorizing {
    /**
     * Match userAuthorities and validAccesses to choose which action to perform.
     * @param userAuthorities list of authorities of user.
     * @param validAccesses set of valid accesses that user must have one of,
     *                      in order to use resources.
     * @param validRun Cannot be null
     * @param invalidRun If null, its run will return null
     * @param error a way to log out errors. Ignore if null.
     * @return Value of validRun if matched, else return value of invalidRun.
     * @param <T> Common type of both run.
     * @throws NullPointerException if userAuthorities or validAccesses or validRun is null
     */
    public static <T> T matchAuthorities(
            Collection<String> userAuthorities,
            Set<Integer> validAccesses,
            Supplier<T> validRun,
            Supplier<T> invalidRun,
            BiConsumer<String, Throwable> error
    ) {
        // and one of authorities must match
        for (var authority : userAuthorities) {
            try {
                var access = EUserAccess.valueOf(authority);
                if (validAccesses.contains(access.value)) {
                    return validRun.get();
                }
            } catch (IllegalArgumentException e) {
                // cant parse authorities -> not valid one but found in DB
                if (error != null) {
                    error.accept("Authority [" + authority + "] found in database but is not a valid Access", e);
                }
            }
        }
        if (invalidRun != null) return invalidRun.get();
        else return null;
    }
}
