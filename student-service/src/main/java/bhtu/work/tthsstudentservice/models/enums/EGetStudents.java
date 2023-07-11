package bhtu.work.tthsstudentservice.models.enums;

public enum EGetStudents {
    ID(1),
    NAME(2),
    SCHOOL(3),
    PARENT(5)
    
    ;//--------------------------------------------
    public final int intVal;

    private EGetStudents(int intVal) {
        this.intVal = intVal;
    }
}
