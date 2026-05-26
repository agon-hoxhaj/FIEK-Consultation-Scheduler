package enums;

public enum DitaJaves {
    Hënë(1),
    Martë(2),
    Mërkurë(3),
    Enjte(4),
    Premte(5),
    Shtunë(6),
    Dielë(7);

    private int id;

    DitaJaves(int id) {
        this.id = id;
    }
    public static DitaJaves fromId(int id) {
        for (DitaJaves dita : DitaJaves.values()) {
            if (dita.getId() == id) {
                return dita;
            }
        }
        return null; // or throw an exception if preferred
    }

    public int getId() {
        return id;
    }

}

