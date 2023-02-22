package Lupta;

import Pokemoni.Antrenor;

public class LuptaCuLogInStdout extends TipDeLupta{
    private final TipDeLupta tipDeLuptaConcret;

    public LuptaCuLogInStdout(TipDeLupta tipDeLuptaConcret) {
        super(tipDeLuptaConcret.getNume());
        this.tipDeLuptaConcret = tipDeLuptaConcret;
    }

    @Override
    public void lupta(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        tipDeLuptaConcret.lupta(antrenor1, antrenor2, this);
    }

    @Override
    public void luptaIntreAntrenori(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        System.out.println(mesajLuptaPokemoni(antrenor1, antrenor2));
        tipDeLuptaConcret.luptaIntreAntrenori(antrenor1, antrenor2, this);
        System.out.println(terminaLupta(antrenor1, antrenor2));
        System.out.println();
    }

    @Override
    public void prepareNextTurn(Antrenor antrenor1, Antrenor antrenor2){
        tipDeLuptaConcret.prepareNextTurn(antrenor1, antrenor2);
        System.out.println(getOutput(antrenor1, antrenor2));
    }
}
