package Lupta;

import Pokemoni.*;

public abstract class TipDeLupta implements Preparable{
    private final String nume;

    public TipDeLupta(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public boolean isDuelOver(Pokemon pokemon1, Pokemon pokemon2){
        int hpCurent1 = pokemon1.getHpCurent();
        int hpCurent2 = pokemon2.getHpCurent();

        return !(hpCurent1 != 0 && hpCurent2 != 0);
    }

    public String getOutput(Antrenor antrenor1, Antrenor antrenor2) {
        Pokemon pokemon1 = antrenor1.getCurrentPokemon();
        Pokemon pokemon2 = antrenor2.getCurrentPokemon();
        String output = pokemon1.getNume() + " ";

        if (antrenor1.getComandaCurenta() != null){
            output += antrenor1.getComandaCurenta().getCaracteristici() + " / " + pokemon2.getNume() + " ";
        }
        else{
            output += "nu face nimic / " + pokemon2.getNume() + " ";
        }
        if (antrenor2.getComandaCurenta() != null){
            output += antrenor2.getComandaCurenta().getCaracteristici() + " -> Rezultat:\n";
        }
        else{
            output += "nu face nimic -> Rezultat:\n";
        }

        output += "a) " + pokemon1.getNume() + " HP " + pokemon1.getHpCurent() + antrenor1.abilitatiInCoolDown() + "\n";
        output += "b) " + pokemon2.getNume() + " HP " + pokemon2.getHpCurent() + antrenor2.abilitatiInCoolDown() + "\n";

        return output;
    }

    public void duelCastigat(Antrenor antrenor1, Antrenor antrenor2){
        Pokemon pokemon1 = antrenor1.getCurrentPokemon();
        Pokemon pokemon2 = antrenor2.getCurrentPokemon();

        if (pokemon1.getHpCurent() == 0 && pokemon2.getHpCurent() != 0){
            pokemon2.duelCastigat(antrenor2);
        }
        else if (pokemon2.getHpCurent() == 0 && pokemon1.getHpCurent() != 0){
            pokemon1.duelCastigat(antrenor1);
        }
    }

    /*
     * Metoda va scrie rezultatul luptei si daca ambii antrenori nu au numele "wild", adica nu sunt
     * "antrenorii" unui Neutrel, atunci se si mentioneaza care antrenor a castigat.
     */
     String terminaLupta(Antrenor antrenor1, Antrenor antrenor2){
        Pokemon pokemon1 = antrenor1.getCurrentPokemon();
        Pokemon pokemon2 = antrenor2.getCurrentPokemon();
        boolean isDuel;
        String mesaj;

        // Verificam ca amandoi antrenorii sa fie antrenori adevarati, nu antrenori falsi construiti pentru Neutreli.
         isDuel = !"wild".equals(antrenor1.getNume()) && !"wild".equals(antrenor2.getNume());

        if (pokemon1.getHpCurent() == 0 && pokemon2.getHpCurent() == 0){
            mesaj = "Draw";
        }
        else if (pokemon1.getHpCurent() == 0){
            mesaj = afiseazaInvingator(antrenor2, isDuel);
        }
        else{
            mesaj = afiseazaInvingator(antrenor1, isDuel);
        }

        return mesaj;
    }

    String afiseazaInvingator(Antrenor antrenor, boolean isDuel) {
        String linie = "Antrenorul " + antrenor.getNume() + " (" + antrenor.getCurrentPokemon().getNume() + ")";

        if (isDuel){
            linie += " castiga Arena invingand pe celalalt antrenor. " + antrenor.getCurrentPokemon().getNume();
        }

        linie += " a castigat si are +1 la toate caracteristicile:\n" + antrenor.getCurrentPokemon().gasesteCaracteristiciGenerale();

        return linie;
    }

    String mesajLuptaPokemoni(Antrenor antrenor1, Antrenor antrenor2){
        Pokemon pokemon1 = antrenor1.getCurrentPokemon();
        Pokemon pokemon2 = antrenor2.getCurrentPokemon();

        return "Se lupta " + pokemon1.getNume() + " cu " + pokemon2.getNume() + "\n" +
                        pokemon1.gasesteCaracteristiciGenerale() + "\n"+
                        pokemon2.gasesteCaracteristiciGenerale() + "\n";
    }

    @Override
    public void luptaIntreAntrenori(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable) {
        Pokemon pokemon1 = antrenor1.getCurrentPokemon();
        Pokemon pokemon2 = antrenor2.getCurrentPokemon();

        while (!isDuelOver(pokemon1, pokemon2)){
            ThreadPtAntrenor tAntrenor1 = new ThreadPtAntrenor(antrenor1, antrenor2);
            ThreadPtAntrenor tAntrenor2 = new ThreadPtAntrenor(antrenor2, antrenor1);
            tAntrenor1.start();
            tAntrenor2.start();
            try {
                tAntrenor1.join();
                tAntrenor2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            preparable.prepareNextTurn(antrenor1, antrenor2);
        }

        duelCastigat(antrenor1, antrenor2);
    }

    @Override
    public void prepareNextTurn(Antrenor antrenor1, Antrenor antrenor2){
        antrenor1.prepareNextTurn();
        antrenor2.prepareNextTurn();
    }

    public abstract void lupta(Antrenor antrenor1, Antrenor antrenor2, Preparable preparable);
}
