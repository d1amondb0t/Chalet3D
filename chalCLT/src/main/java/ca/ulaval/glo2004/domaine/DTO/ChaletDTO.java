package ca.ulaval.glo2004.domaine.DTO;



import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.SensToit;
import ca.ulaval.glo2004.domaine.Toit;
import ca.ulaval.glo2004.domaine.utils.Imperial;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class ChaletDTO {
    private List<Mur> murs;
    private List <Toit> toits;
    private ImperialDTO largeur;
    private ImperialDTO hauteur;
    private ImperialDTO epaisseurMur;
    private ImperialDTO minDistEntreAccessoire;
    private SensToit sens;
    public UUID Uuid;

    public ChaletDTO(Chalet chalet) {
        this.largeur = new ImperialDTO(chalet.getLargeur());
        this.hauteur = new ImperialDTO(chalet.getHauteur());
        this.epaisseurMur = new ImperialDTO(chalet.getEpaisseurMur());
        this.minDistEntreAccessoire = new ImperialDTO(chalet.getMinDistEntreAccessoire());
        this.sens = chalet.getSens();
    }


    public UUID getUuid() {
        return Uuid;
    }

}


