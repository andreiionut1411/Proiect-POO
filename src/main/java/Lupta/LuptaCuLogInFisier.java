package Lupta;

import Pokemoni.Antrenor;

import java.io.FileWriter;
import java.io.IOException;

public class LuptaCuLogInFisier extends TipDeLupta{
    private final TipDeLupta tipDeLuptaConcret;
    private final String numeFisierDeIesire;

    public LuptaCuLogInFisier(TipDeLupta tipDeLupta, String numeFisierIntrare) {
        super(tipDeLupta.getNume());
        this.tipDeLuptaConcret = tipDeLupta;
        String[] nume = numeFisierIntrare.split("/");
        nume = nume[nume.length - 1].split("\\.");
        this.numeFisierDeIesire = nume[0] + ".out";
    }

    @Override
    public void lupta(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        tipDeLuptaConcret.lupta(antrenor1, antrenor2, this);
    }

    @Override
    public void luptaIntreAntrenori(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {

        try(FileWriter fileWriter = new FileWriter("src/main/resources/Log/" + numeFisierDeIesire, true)){
           fileWriter.write(mesajLuptaPokemoni(antrenor1, antrenor2) + "\n");
           fileWriter.flush();
           tipDeLuptaConcret.luptaIntreAntrenori(antrenor1, antrenor2, this);
           fileWriter.write(terminaLupta(antrenor1, antrenor2) + "\n");
           fileWriter.write("\n");
        }
        catch (IOException e){
            System.out.println("A fost o problema la scrierea in fisier");
        }
    }

    @Override
    public void prepareNextTurn(Antrenor antrenor1, Antrenor antrenor2) {
        tipDeLuptaConcret.prepareNextTurn(antrenor1, antrenor2);

        try(FileWriter fileWriter = new FileWriter("src/main/resources/Log/" + numeFisierDeIesire, true)){
            fileWriter.write(getOutput(antrenor1, antrenor2) + "\n");
        }
        catch (IOException e){
            System.out.println("A fost o problema la scrierea in fisier");
        }
    }
}
