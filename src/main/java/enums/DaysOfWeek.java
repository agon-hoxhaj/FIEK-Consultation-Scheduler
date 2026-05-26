package enums;

public enum DaysOfWeek {
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7);

    private int id;

    DaysOfWeek(int id){
        this.id=id;
    }
    public static DaysOfWeek fromId(int id) {
        for (DaysOfWeek dita : DaysOfWeek.values()) {
            if (dita.getId() == id) {
                return dita;
            }
        }
        return null;
    }

    public int getId(){
        return id;
    }

}
