package Lupta;

import Pokemoni.Antrenor;

public class Duel extends TipDeLupta{
    public Duel() {
        super("Duel");
    }

    @Override
    public void lupta(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        preparable.luptaIntreAntrenori(antrenor1, antrenor2, preparable);
    }
}
