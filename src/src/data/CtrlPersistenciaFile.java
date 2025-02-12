package src.data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.*;
import java.util.Date;

public class CtrlPersistenciaFile {

    private final String base_path = "../../data/persistence"; // Direccio on es guarden les dades

    // Fitxers que conte els noms dels teclats, layouts i alfabets, juntament  amb les direccions
    // dels fixters on es guarda el contingut dels mateixos.
    private final String keyboards_file = "keyboards_db.json";
    private final String alfabets_file = "alfabets_db.json";
    private final String layouts_file = "layouts_db.json";

    private Date last_update;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private String date;

    // Aquí s'implementa l'extracció de la informació dels diferents JSONs on es guarda tot.
    private static CtrlPersistenciaFile singletonPersistenciaFile;

    private CtrlPersistenciaFile() {}

    public static CtrlPersistenciaFile getInstance() {
        if(singletonPersistenciaFile == null) singletonPersistenciaFile = new CtrlPersistenciaFile();
        return singletonPersistenciaFile;
    }

    public String lastSave() {
        return date;
    }

    /**
     *  Llegeix el fitxer filepath, retorna el seu contingut en forma de String.
     */
    private String readFileStr(String filepath) throws IOException {
        FileReader fr = new FileReader(filepath);
        BufferedReader br = new BufferedReader(fr);
        String content = "";            
        String line = br.readLine();
        while (line != null) {
            content += line + '\n';
            line = br.readLine();
        }
        br.close();
        return content;
    }

    /**
     *  Modifica l'estat d'un element de tipus 'type', que pot ser "teclats", "alfabets" o "layouts".
     *  En el cas que content sigui NULL, esborra l'element 'id' del tipus 'type' de l'emmagatzematge.
     *  En el cas que content sigui diferent de NUll:
     *      - Si ja existeix un fixter corresponent a id i type, actualitza el contingut amb el que
     *        rep al parametre 'content'.
     *      - Si no existeix el fitxer, el crea amb el contingut de 'content' dintre.
     */
    public void saveState(String type, String id, String content) throws IOException {
        String db_path = base_path;
        String tg_dir = "";
        if (type.equals("teclats")) {
            db_path += "/" + keyboards_file;
            tg_dir = "keyboards";
        }
        else if (type.equals("alfabets")) {
            db_path += "/" + alfabets_file;
            tg_dir = "alfabets";
        }
        else if (type.equals("layouts")) {
            db_path += "/" + layouts_file;
            tg_dir = "layouts";
        }

        String db_content = readFileStr(db_path);
        JSONObject json_db = new JSONObject(db_content);

        // Actualitzar objecte json_db + crear/esborrar el fitxer necessari
        if (content == null) { // Delete mode
            // Borrar fitxer corresponent + entrada a la DB
            String filepath = base_path + "/" + json_db.getString(id);
            File file = new File(filepath);
            file.delete();
            json_db.remove(id);
        }
        else {
            // Si existeix l'entrada a la DB, actualitzar contingut del fitxer
            if (json_db.has(id)) { 
                String filepath = base_path + "/" + json_db.getString(id);
                FileWriter fw = new FileWriter(filepath, false); // flag false permet sobreescriure
                fw.write(content);
                fw.close();
            }
            // Si NO existeix l'entrada a la DB, crear entrada + crear fitxer + escriure contingut
            else {
                String new_filepath = tg_dir + "/" + id + ".json";
                json_db.put(id, new_filepath);
                String filepath = base_path + "/" + new_filepath;
                FileWriter fw = new FileWriter(filepath, false);
                fw.write(content);
                fw.close();
            }
        }

        // Actualitzar el fitxer que conte la json_db
        FileWriter fw = new FileWriter(db_path, false); // flag false permet sobreescriure
        fw.write(json_db.toString(4));
        fw.close();

        last_update = new Date();
        date = formatter.format(last_update);
    }

    /**
     *  Retorna un ArrayList amb les direccions de tots els elements emmagatzemats del tipus 'type'.
     */
    public ArrayList<String> getAll(String type) throws IOException {
        last_update = new Date();
        date = formatter.format(last_update);

        String db_path = base_path;
        if (type.equals("teclats")) db_path += "/" + keyboards_file;
        else if (type.equals("alfabets")) db_path += "/" + alfabets_file;
        else if (type.equals("layouts")) db_path += "/" + layouts_file;
        
        String db_content = readFileStr(db_path);
        JSONObject json_db = new JSONObject(db_content);
        ArrayList<String> res = new ArrayList<>();

        for (String key : json_db.keySet()) {
            String aux = json_db.getString(key);
            res.add(aux);
        }
        return res;
    }

    /**
     *  Retorna el contingut del fitxer 'filename' en forma de string, on 'filename' es una de les
     *  direccions dels fixters emmagatzemades en layouts_db, keyboards_db o alfabets_db.
     */
    public String getObject(String filename) throws IOException {
        String filepath = base_path + '/' + filename;
        return readFileStr(filepath);
    }
}
