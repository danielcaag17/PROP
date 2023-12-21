package src.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

public class CtrlPersistenciaFile {

    private final String base_path = "../../../data/persistence";

    private final String keyboards_file = "keyboards_db.json";
    private final String alfabets_file = "alfabets_db.json";
    private final String layouts_file = "layouts_db.json";

    // Aquí s'implementa l'extracció de la informació dels diferents JSONs on es guarda tot.
    private static CtrlPersistenciaFile singletonPersistenciaFile;


    private CtrlPersistenciaFile() {

    }

    public static CtrlPersistenciaFile getInstance() {
        if(singletonPersistenciaFile == null) singletonPersistenciaFile = new CtrlPersistenciaFile();
        return singletonPersistenciaFile;
    }

    private String readFileStr(String filepath) throws IOException {
        FileReader fr = new FileReader(filepath);
        BufferedReader br = new BufferedReader(fr);
        String content = "";            
        String line = br.readLine();
        while (line != null) {
            content += line + '\n';
            line = br.readLine();
        }
        return content;
    }

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
            String filepath = base_path + json_db.getJSONObject(id).getString(id);
            File file = new File(filepath);
            file.delete();
            json_db.remove(id);
        }
        else {
            // Si existeix l'entrada a la DB, actualitzar contingut del fitxer
            if (json_db.has(id)) { 
                String filepath = base_path + json_db.getJSONObject(id).getString(id);
                FileWriter fw = new FileWriter(filepath, false); // flag false permet sobreescriure
                fw.write(content);
                fw.close();
            }
            // Si NO existeix l'entrada a la DB, crear entrada + crear fitxer + escriure contingut
            else {
                String new_filepath = tg_dir + "/" + id + ".json";
                json_db.put(id, new_filepath);
                String filepath = base_path + new_filepath;
                File file = new File(filepath);
                FileWriter fw = new FileWriter(filepath, false);
                fw.write(content);
                fw.close();
            }
        }

        // Actualitzar el fitxer que conte la json_db
        FileWriter fw = new FileWriter(db_path, false); // flag false permet sobreescriure
        fw.write(json_db.toString(4));
        fw.close();
    }

    public ArrayList<String> getAll(String tipus) throws IOException {
        String db_path = base_path;
        if (type.equals("teclats")) db_path += "/" + keyboards_file;
        else if (type.equals("alfabets")) db_path += "/" + alfabets_file;
        else if (type.equals("layouts")) db_path += "/" + layouts_file;
        
        String db_content = readFileStr(db_path);
        JSONObject json_db = new JSONObject(db_content);
        ArrayList<String> res = new ArrayList<>();

        for (String key : json_db.keys()) {
            String aux = json_db.getJSONObject(key).getString(key);
            res.add(aux);
        }
        return res;
    }

    public String getObject(String filename) throws IOException {
        String filepath = base_path + '/' + filename;
        return readFileStr(filepath);
    }
}
