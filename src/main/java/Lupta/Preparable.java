package Lupta;

import Pokemoni.Antrenor;

public interface Preparable {
    void luptaIntreAntrenori(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable);
    void prepareNextTurn(Antrenor antrenor1, Antrenor antrenor2);
}
