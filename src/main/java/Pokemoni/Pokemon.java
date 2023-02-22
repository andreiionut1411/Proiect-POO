package Pokemoni;

import Items.*;

import java.util.HashSet;
import java.util.Set;

public class Pokemon implements Comparable<Pokemon>{
    private final String nume;
    private int hp;
    private int hpCurent;
    private int attack;
    private int specialAttack;
    private int defense;
    private int specialDefense;
    private SpecialAbility ability1;
    private SpecialAbility ability2;
    private final Set<Item> specialItems;
    private State stareCurenta;
    private State starePrecedenta;
    private final State isIdle;
    private final State isDodging;
    private final State isStunned;
    private boolean wasAttacked;
    /*
     * Valoarea wasAttacked ne va indica daca adversarul a atacat Pokemonul curent. Aceasta variabila
     * este utila in cazul duelului cand avem multithreading intrucat nu stim in ce ordine se vor executa instructiunile.
     * In plus, aceasta variabila ne va indica in functie de stare si ce trebuie sa faca Pokemonul.
     */

    public Pokemon(String nume, int hp, int attack, int specialAttack,
                   int defense, int specialDefense) {
        this.nume = nume;
        this.hp = hp;
        this.hpCurent = hp;
        this.attack = attack;
        this.specialAttack = specialAttack;
        this.defense = defense;
        this.specialDefense = specialDefense;
        this.specialItems = new HashSet<>();
        this.isIdle = new IsIdle(this);
        this.isDodging = new IsDodging(this);
        this.isStunned = new IsStunned(this);
        this.stareCurenta = isIdle;
        this.starePrecedenta = isIdle;
        this.wasAttacked = false;
    }

    public String getNume() {
        return nume;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getHpCurent() {
        return hpCurent;
    }

    public SpecialAbility getAbility1() {
        return ability1;
    }

    public SpecialAbility getAbility2() {
        return ability2;
    }

    public Set<Item> getSpecialItems() {
        return specialItems;
    }

    public State getIsIdle() {
        return isIdle;
    }

    public State getIsDodging() {
        return isDodging;
    }

    public State getIsStunned() {
        return isStunned;
    }

    public State getStareCurenta() {
        return stareCurenta;
    }

    public State getStarePrecedenta() {
        return starePrecedenta;
    }

    public boolean isWasAttacked() {
        return wasAttacked;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setHpCurent(int hpCurent) {
        this.hpCurent = hpCurent;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public void setAbilities (SpecialAbility ability1, SpecialAbility ability2){
        this.ability1 = ability1;
        this.ability2 = ability2;
    }

    public void setStareCurenta(State stareCurenta) {
        this.stareCurenta = stareCurenta;
    }

    public void setWasAttacked(boolean wasAttacked) {
        this.wasAttacked = wasAttacked;
    }

    public void setStarePrecedenta(State starePrecedenta) {
        this.starePrecedenta = starePrecedenta;
    }

    /*
     * Folosim un Set pentru a tine minte ce obiecte speciale a primit Pokemonul, si daca se
     * inceara sa i se dea de 2 ori acelasi Item, atunci aceasta a 2-a incercare nu va fi bagata
     * in seama intrucat in Set nu avem dubluri.
     */
    public void receiveItem (Item specialItem){
        boolean isNewItem;
        int NR_MAXIM_ITEMS = 3;
        if (specialItems.size() >= NR_MAXIM_ITEMS)
            System.out.println("Nu mai se pot oferi obiecte Pokemon-ului " + nume);

        isNewItem = specialItems.add(specialItem);

        // Folosim efectul obiectului doar daca este un obiect nouu care nu este deja in Set.
        if (isNewItem)
            specialItem.useItem(this);
    }

    public int calcularePutere(){
        return hp + attack + specialAttack + defense + specialDefense;
    }

    public void prepareNextTurn(Antrenor antrenor){
        if (ability1 != null && ability2 != null) {
            ability1.oneTurnPassed(antrenor);
            ability2.oneTurnPassed(antrenor);
        }

        // Verificam daca Pokemonul a lesinat sau nu.
        if (hpCurent < 0){
            hpCurent = 0;
        }

        stareCurenta.nextTurn();
        wasAttacked = false;
    }

    public void resetAbilities(Antrenor antrenor){
        ability1.resetTimeUntilIsAvailable(antrenor);
        ability2.resetTimeUntilIsAvailable(antrenor);
    }

    // Metoda actualizeaza caracteristicile atunci cand Pokemonul castiga si reseteaza timpul pentru abilitati.
    public void duelCastigat(Antrenor antrenor){
        hp++;
        hpCurent = hp;
        defense++;
        specialDefense++;

        if (attack != 0)
            attack++;
        else if (specialAttack != 0)
            specialAttack++;

        resetAbilities(antrenor);
    }

    public String gasesteCaracteristiciGenerale(){
        return gasesteCaracteristici(hp);
    }

    private String gasesteCaracteristici(int hp){
        String informatii =  nume + " are " + hp + " hp, ";

        if (attack != 0)
            informatii = informatii + "atack de " + attack;
        else
            informatii = informatii + "specialAttack de " + specialAttack;

        informatii = informatii + ", defense de " + defense + " si specialDenfense de " + specialDefense;

        return informatii;
    }

    public void executaComanda(Attack comanda, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers){
        State.comandaInFctDeStare(comanda, this, opponentPokemon, antrenor, antrenorAdvers);
    }

    @Override
    public int compareTo(Pokemon o) {
        int puterePokemonCurent = calcularePutere();

        if (puterePokemonCurent == o.calcularePutere())
            return o.getNume().compareTo(nume);

        return puterePokemonCurent - o.calcularePutere();
    }
}
