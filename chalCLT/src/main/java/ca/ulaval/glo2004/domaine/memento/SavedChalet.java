package ca.ulaval.glo2004.domaine.memento;
import java.io.*;


import ca.ulaval.glo2004.domaine.Chalet;

public class SavedChalet implements java.io.Serializable{
    public Chalet chalet;
    private static final long serialVersionUID = 6529685098267757690L;


    public SavedChalet(Chalet chaletSave){
        chalet = chaletSave;
    }


public void createFile(String cheminFichier, String nomFichier, SavedChalet chalet) throws IOException {
    try (final FileOutputStream fout = new FileOutputStream(cheminFichier+ nomFichier + ".clt");
         final ObjectOutputStream out = new ObjectOutputStream(fout)) {
        out.writeObject(chalet);
        out.flush();
}}


    public Object readFile(File fichier) throws IOException, ClassNotFoundException {
        Object result = null;
        try (FileInputStream fis = new FileInputStream(fichier);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            result = ois.readObject();
            ois.close();
            fis.close();
            return result;
        }


    }
}
