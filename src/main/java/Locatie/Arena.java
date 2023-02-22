package Locatie;

import Lupta.TipDeLupta;
import Pokemoni.Antrenor;

public class Arena extends Locatie{

    public Arena(String pathToAventura) {
        super(pathToAventura);
    }

    /*
     * Daca numar = 0, atunci se alege primul Pokemon, 1 pentru al 2-lea si 2 pentru al 3-lea.
     * Daca numar = 3, atunci se va alege cel mai bun Pokemon pentru fiecare antrenor.
     */
    private void alegerePokemon(int numar) {
        Antrenor antrenor1 = getOrganizator().getAntrenor1();
        Antrenor antrenor2 = getOrganizator().getAntrenor2();

        if (numar == 3){
            antrenor1.setBestPokemon();
            antrenor2.setBestPokemon();
        }
        else {
            antrenor1.setCurrentPokemon(numar);
            antrenor2.setCurrentPokemon(numar);
        }
    }

    public void pregatesteLupta(){
        Organizator organizator = getOrganizator();
        organizator.pregatesteTipDeLupta();
    }

    @Override
    public void pregatesteAntrenori() {
        Organizator organizator = getOrganizator();
        organizator.intrareAntrenoriInArena(getPathToAventura());
    }

    @Override
    public void pregatesteArena(int numar) {
        alegerePokemon(numar);
    }

    @Override
    public void incepeAventura() {
        Organizator organizator = getOrganizator();
        Antrenor antrenor1 = organizator.getAntrenor1();
        Antrenor antrenor2 = organizator.getAntrenor2();
        TipDeLupta tipDeLupta = organizator.getTipDeLupta();

        tipDeLupta.lupta(antrenor1, antrenor2, tipDeLupta);
    }
}
