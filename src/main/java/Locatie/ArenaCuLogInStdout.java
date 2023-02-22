package Locatie;

import Lupta.*;
import Pokemoni.Antrenor;

public class ArenaCuLogInStdout extends Locatie{
    private final Arena arena;

    public ArenaCuLogInStdout(Arena arena) {
        super(arena.getPathToAventura());
        this.arena = arena;
    }

    @Override
    public void pregatesteAntrenori() {
        Organizator organizator = arena.getOrganizator();
        arena.pregatesteAntrenori();
        Antrenor antrenor1 = organizator.getAntrenor1();
        Antrenor antrenor2 = organizator.getAntrenor2();
        System.out.println(prezeintaAntrenori(antrenor1, antrenor2));
    }

    @Override
    public void pregatesteArena(int numar) {
        Organizator organizator = arena.getOrganizator();
        arena.pregatesteArena(numar);
        Antrenor antrenor1 = organizator.getAntrenor1();
        Antrenor antrenor2 = organizator.getAntrenor2();

        System.out.println(prezintaPokemoni(antrenor1, antrenor2, numar));
    }

    @Override
    public void pregatesteLupta() {
        arena.pregatesteLupta();
        TipDeLupta tipDeLupta = getOrganizator().getTipDeLupta();
        TipDeLupta tipDeLuptaLog = new LuptaCuLogInStdout(tipDeLupta);
        getOrganizator().setTipDeLupta(tipDeLuptaLog);
        System.out.println("Arena a ales evenimentul: " + tipDeLuptaLog.getNume());
    }

    @Override
    public void incepeAventura() {
        arena.incepeAventura();
    }

    @Override
    public void lupta() {
        super.lupta();
    }
}
