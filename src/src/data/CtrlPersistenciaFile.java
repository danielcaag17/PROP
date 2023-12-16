package src.data;

public class CtrlPersistenciaFile {
    // Aquí s'implementa l'extracció de la informació dels diferents JSONs on es guarda tot.
    private static CtrlPersistenciaFile singletonPersistenciaFile;


    private CtrlPersistenciaFile() {

    }

    public static CtrlPersistenciaFile getInstance() {
        if(singletonPersistenciaFile == null) singletonPersistenciaFile = new CtrlPersistenciaFile();
        return singletonPersistenciaFile;
    }

    public void saveState(String tipus, String nom, String data) {

    }

    public String[] getAll(String tipus) {
        return null; // provisional
    }

    public String getObject(String filename) {
        return null; // provisional
    }
}
