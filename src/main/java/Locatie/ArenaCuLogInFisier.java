package Locatie;

import Lupta.*;
import Pokemoni.Antrenor;

import java.io.FileWriter;
import java.io.IOException;

public class ArenaCuLogInFisier extends Locatie{
    private final Arena arena;
    private final String numeFisierDeIesire;

    public ArenaCuLogInFisier(Arena arena) {
        super(arena.getPathToAventura());
        this.arena = arena;
        String[] nume = arena.getPathToAventura().split("/");
        nume = nume[nume.length - 1].split("\\.");
        this.numeFisierDeIesire = nume[0] + ".out";
    }

    @Override
    public void pregatesteAntrenori() {
        Organizator organizator = arena.getOrganizator();
        arena.pregatesteAntrenori();
        Antrenor antrenor1 = organizator.getAntrenor1();
        Antrenor antrenor2 = organizator.getAntrenor2();

        try(FileWriter fileWriter = new FileWriter("src/main/resources/Log/" + numeFisierDeIesire)){
            fileWriter.write(prezeintaAntrenori(antrenor1, antrenor2) + "\n");
        }
        catch (IOException e){
            System.out.println("A fost o problema la scrierea in fisier");
        }
    }

    @Override
    public void pregatesteArena(int numar) {
        Organizator organizator = arena.getOrganizator();
        arena.pregatesteArena(numar);
        Antrenor antrenor1 = organizator.getAntrenor1();
        Antrenor antrenor2 = organizator.getAntrenor2();

        try(FileWriter fileWriter = new FileWriter("src/main/resources/Log/" + numeFisierDeIesire, true)){
            fileWriter.write(prezintaPokemoni(antrenor1, antrenor2, numar) + "\n");
        }
        catch (IOException e){
            System.out.println("A fost o problema la scrierea in fisier");
        }
    }

    @Override
    public void pregatesteLupta() {
        arena.pregatesteLupta();
        TipDeLupta tipDeLupta = getOrganizator().getTipDeLupta();
        TipDeLupta tipDeLuptaCuLog = new LuptaCuLogInFisier(tipDeLupta, getPathToAventura());
        getOrganizator().setTipDeLupta(tipDeLuptaCuLog);

        try(FileWriter fileWriter = new FileWriter("src/main/resources/Log/" + numeFisierDeIesire, true)){
            fileWriter.write("Arena a ales evenimentul: " + tipDeLuptaCuLog.getNume() + "\n");
        }
        catch (IOException e){
            System.out.println("A fost o problema la scrierea in fisier");
        }
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
