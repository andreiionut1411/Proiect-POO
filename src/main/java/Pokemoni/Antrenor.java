package Pokemoni;

import Items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Antrenor {
    private final String nume;
    private final int varsta;
    private final ArrayList<Pokemon> pokemoni;
    private Pokemon currentPokemon;
    private List<Item> borseta;
    private final List<Attack> comenziPosibile;
    private Attack comandaCurenta;

    public Antrenor(String nume, int varsta){
        this.nume = nume;
        this.varsta = varsta;
        this.pokemoni = new ArrayList<>();
        this.comenziPosibile = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public ArrayList<Pokemon> getPokemoni() {
        return pokemoni;
    }


    public Pokemon getCurrentPokemon() {
        return currentPokemon;
    }

    public List<Item> getBorseta() {
        return borseta;
    }

    public Attack getComandaCurenta() {
        return comandaCurenta;
    }

    public void setCurrentPokemon(int numarPokemon) {
        this.currentPokemon = pokemoni.get(numarPokemon);
        resetComenzi();
        adaugaComanda(new NormalAttack(currentPokemon));
        adaugaComanda(currentPokemon.getAbility1());
        adaugaComanda(currentPokemon.getAbility2());
    }

    public void setBestPokemon(){
        currentPokemon = pokemoni.get(0);
        int indiceBestPokemon = 0;

        for (int i = 1; i < 3; i++) {
            Pokemon pokemon = pokemoni.get(i);
            if (pokemon.compareTo(currentPokemon) > 0) {
                indiceBestPokemon = i;
                currentPokemon = pokemoni.get(i);
            }
        }

        // Trebuie sa setam asa Pokemonul pentru ca altfel nu vor fi setate corect comenzile posibile.
        currentPokemon.resetAbilities(this);
        setCurrentPokemon(indiceBestPokemon);

        // In caz ca cel mai bun Pokemon a pierdut, ii dam viata inapoi pentru noua lupta.
        currentPokemon.setHpCurent(currentPokemon.getHp());
    }

    public void setComandaCurenta() {
        int numarComenziDisponibile = comenziPosibile.size();
        int numarComandaAleatoare = new Random().nextInt(numarComenziDisponibile);
        comandaCurenta = comenziPosibile.get(numarComandaAleatoare);
    }

    private void resetComenzi(){
        while (!comenziPosibile.isEmpty()){
            comenziPosibile.remove(0);
        }
    }

    // Daca Pokemonul este stunned, comanda este null.
    public void commandIsStunned(){
        comandaCurenta = null;
    }

    public void adaugaComanda(Attack comanda){
        if (comanda != null)
            comenziPosibile.add(comanda);
    }

    public void scoateComanda(Attack comanda){
        comenziPosibile.remove(comanda);
    }

    public void addPokemon (Pokemon pokemon){
        pokemoni.add(pokemon);
    }

    public Pokemon findPokemon (String numePokemon){
        Pokemon searchedPokemon = null;

        for (Pokemon pokemon: pokemoni) {
            if (pokemon.getNume().equals(numePokemon))
                searchedPokemon = pokemon;
        }

        return searchedPokemon;
    }

    public void giveItemToPokemon (Pokemon pokemon, Item item){
        pokemon.receiveItem(item);
    }

    public void primesteBorseta(ArrayList<Item> borseta){
        this.borseta = borseta;
    }

    public String abilitatiInCoolDown(){
        String abilitati = "";

        if (currentPokemon.getAbility1() != null) {
            if (currentPokemon.getAbility1().getTimeUntilIsAvailable() != 0) {
                abilitati += ", abilitate 1 cooldown " + currentPokemon.getAbility1().getTimeUntilIsAvailable();
            }
        }

        if (currentPokemon.getAbility2() != null) {
            if (currentPokemon.getAbility2().getTimeUntilIsAvailable() != 0) {
                abilitati += ", abilitate 2 cooldown " + currentPokemon.getAbility2().getTimeUntilIsAvailable();
            }
        }

        return abilitati;
    }

    public void daComandaDeAtac(Pokemon opponentPokemon, Antrenor antrenorAdvers){
        currentPokemon.executaComanda(comandaCurenta, opponentPokemon, this, antrenorAdvers);
    }

    public void luptaPentruOTura(Pokemon opponentPokemon, Antrenor antrenorAdvers){
        setComandaCurenta();
        daComandaDeAtac(opponentPokemon, antrenorAdvers);
    }

    public void prepareNextTurn(){
        currentPokemon.prepareNextTurn(this);
    }
}
