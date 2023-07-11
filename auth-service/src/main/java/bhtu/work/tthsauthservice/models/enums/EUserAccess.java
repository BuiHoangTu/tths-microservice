package bhtu.work.tthsauthservice.models.enums;

public enum EUserAccess {
    READ_A_STUDENT (10),
    FIX_A_STUDENT_DETAIL (11),
    READ_REGIONAL_STUDENTS (20),
    WRITE_REGIONAL_STUDENTS (21),
    READ_REGIONAL_SATISTIC (23),
    READ_NATIONAL_STUDENTS (30),
    WRITE_NATIONAL_STUDENTS (31),
    READ_NATIONAL_SATISTIC (32)
    ;
    //----------------------------------------------
    public final int value;

    private EUserAccess(int value) {
        this.value = value;
    }
}
