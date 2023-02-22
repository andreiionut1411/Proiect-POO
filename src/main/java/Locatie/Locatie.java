package Locatie;

import Pokemoni.*;

public abstract class Locatie {
    private final String pathToAventura;
    private final Organizator organizator;

    public Locatie(String pathToAventura) {
        this.pathToAventura = pathToAventura;
        this.organizator = Organizator.getOrganizator();
    }

    public Organizator getOrganizator() {
        return organizator;
    }

    public String getPathToAventura() {
        return pathToAventura;
    }

    String caracteristiciAntrenor(Antrenor antrenor){
        int nrPokemoni = antrenor.getPokemoni().size();
        StringBuilder caracteristici = new StringBuilder(antrenor.getNume() + " de " +
                antrenor.getVarsta() + " ani care are Pokemonii: ");

        for (int i = 0; i < nrPokemoni - 1; i++){
            Pokemon pokemon = antrenor.getPokemoni().get(i);
            caracteristici.append(pokemon.getNume()).append(", ");
        }

        Pokemon ultimulPokemon = antrenor.getPokemoni().get(nrPokemoni - 1);
        caracteristici.append(ultimulPokemon.getNume());

        return caracteristici.toString();
    }

    String prezeintaAntrenori(Antrenor antrenor1, Antrenor antrenor2){

        return "In arena au intrat:\n" +
                        caracteristiciAntrenor(antrenor1) + "\n" +
                        caracteristiciAntrenor(antrenor2) + "\n";
    }

    String prezintaPokemoni(Antrenor antrenor1, Antrenor antrenor2, int numar){
        String mesaj = "";

        if (numar == 3){
            mesaj += "Se dueleaza cei mai buni Pokemoni ai antrenorilor:\n";
        }
        else{
            mesaj += "Se vor duela Pokemonii:\n";
        }

        mesaj += antrenor1.getCurrentPokemon().gasesteCaracteristiciGenerale() + "\n" +
                 antrenor2.getCurrentPokemon().gasesteCaracteristiciGenerale() + "\n";

        return mesaj;
    }

    public void aventura(){
        pregatesteLupta();

        while (!(getOrganizator().getTipDeLupta().getNume().equals("Duel"))){
            incepeAventura();
            pregatesteLupta();
        }

        incepeAventura();
    }

    public void lupta(){
        pregatesteAntrenori();

        for (int i = 0; i < 4; i++){
            pregatesteArena(i);
            aventura();
        }
    }

    public abstract void pregatesteAntrenori();
    public abstract void pregatesteArena(int numar);
    public abstract void pregatesteLupta();
    public abstract void incepeAventura();
}
