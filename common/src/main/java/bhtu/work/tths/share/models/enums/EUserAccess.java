package bhtu.work.tths.share.models.enums;

public enum EUserAccess {
    ADMIN (0),
    READ_A_STUDENT (10),
    FIX_A_STUDENT_DETAIL (11),
    READ_REGIONAL_STUDENTS (20),
    WRITE_REGIONAL_STUDENTS (21),
    READ_REGIONAL_STATISTIC (23),
    READ_NATIONAL_STUDENTS (30),
    WRITE_NATIONAL_STUDENTS (31),
    READ_NATIONAL_STATISTIC (32)
    ;
    //----------------------------------------------
    public final int value;

    private EUserAccess(int value) {
        this.value = value;
    }
}
