package Locatie;

import Lupta.*;
import Pokemoni.Antrenor;

import java.util.ArrayList;
import java.util.Random;

public class Organizator {
    private Antrenor antrenor1;
    private Antrenor antrenor2;
    private final ExternalFilesAdapter adapter;
    private TipDeLupta tipDeLupta;
    private static Organizator instantaOrganizator = new Organizator(new ExternalFilesAdapter());

    private Organizator(ExternalFilesAdapter externalFilesAdapter) {
        this.adapter = externalFilesAdapter;
    }

    public static Organizator getOrganizator(){
        return instantaOrganizator;
    }

    public Antrenor getAntrenor1() {
        return antrenor1;
    }

    public Antrenor getAntrenor2() {
        return antrenor2;
    }

    public TipDeLupta getTipDeLupta() {
        return tipDeLupta;
    }

    public void intrareAntrenoriInArena(String filePathToAdventure){
        ArrayList<Antrenor> antrenori = adapter.pregatesteAntrenoriPentruAventura(filePathToAdventure);

        antrenor1 = antrenori.get(0);
        antrenor2 = antrenori.get(1);
    }

    private TipDeLupta creazaTipDeLupta(){
        int luptaRandom = new Random().nextInt(3);

        if (luptaRandom == 0)
            return new LuptaNeutrel(1);
        else if (luptaRandom == 1)
            return new LuptaNeutrel(2);
        else
            return new Duel();
    }

    public void pregatesteTipDeLupta(){
        this.tipDeLupta = creazaTipDeLupta();
    }

    public void setTipDeLupta(TipDeLupta tipDeLupta) {
        this.tipDeLupta = tipDeLupta;
    }
}
