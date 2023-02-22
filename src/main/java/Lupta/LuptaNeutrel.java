package Lupta;

import Pokemoni.*;

public class LuptaNeutrel extends TipDeLupta{
    private final int numarNeutrel;

    public LuptaNeutrel(int numarNeutrel) {
        super("Lupta Neutrel" + numarNeutrel);
        this.numarNeutrel = numarNeutrel;
    }

    private Pokemon creazaNeutrel(int numarNeutrel) {
        Pokemon neutrel;
        if (numarNeutrel == 1) {
            neutrel = new Pokemon("Neutrel1", 10, 3, 0, 1, 1);
        }
        else {
            neutrel = new Pokemon("Neutrel2", 20, 4, 0, 1, 1);
        }

        neutrel.setAbilities(null, null);

        return neutrel;
    }

    private Antrenor pregatesteWildNeutrel(){
        Pokemon neutrel = creazaNeutrel(numarNeutrel);
        Antrenor noAntrenor = new Antrenor("wild", 0);
        noAntrenor.addPokemon(neutrel);
        noAntrenor.setCurrentPokemon(0);

        return noAntrenor;
    }

    @Override
    public void lupta(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        Antrenor noAntrenor = pregatesteWildNeutrel();
        preparable.luptaIntreAntrenori(antrenor1, noAntrenor, preparable);
        noAntrenor = pregatesteWildNeutrel();
        preparable.luptaIntreAntrenori(antrenor2, noAntrenor, preparable);
    }
}
