package Locatie;

import Items.*;
import Pokemoni.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExternalFilesAdapter {

    private ArrayList<Item> creareBorseta(){
        ArrayList<Item> borseta = new ArrayList<>();

        borseta.add(new Scut());
        borseta.add(new Vesta());
        borseta.add(new Sabiuta());
        borseta.add(new BaghetaMagica());
        borseta.add(new Vitamine());
        borseta.add(new BradDeCraciun());
        borseta.add(new Pelerina());

        return borseta;
    }

    /*
     * Metoda primeste numele fisierului in care avem stocate informatiile despre
     * Pokemoni, si cauta in acest fisier numele unui anumit Pokemon. Metoda intoarce
     * Pokemonul cautat, construit cu toate caracteristicile necesare.
     */
    public Pokemon obtainPokemon (String fileWithPokemon, String pokemonName){
        Pokemon pokemon = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileWithPokemon))) {
            String line = br.readLine();

            while (line != null){
                if (line.equals(pokemonName)){
                    SpecialAbility ability1, ability2;

                    pokemon = readPokemonFromReader(br, line);
                    ability1 = getAbilityFromReader(br, "Abilitate 1");
                    ability2 = getAbilityFromReader(br, "Abilitate 2");
                    pokemon.setAbilities(ability1, ability2);
                    break;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pokemon;
    }

    /*
     * Metoda primeste numele fisierului de intrare al aventurii, si pregateste atributele
     * celor 2 antrenori care intra in arena, plus lista de Pokemoni ai fiecaruia.
     * Din fisier stim ca vom citi 3 Pokemoni, dar in metoda nu am pus nicio constrangere de
     * acest gen, putand astfel usor sa refolosim metoda si daca antrenorii au mai multi Pokemoni.
     */
    public ArrayList<Antrenor> pregatesteAntrenoriPentruAventura(String filePathToAdventure){
        ArrayList<Antrenor> antrenori = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePathToAdventure))) {
            // Avem doar 2 antrenori care intra in arena.
            for (int i = 0; i < 2; i++){
                Antrenor antrenor = getReadyAntrenorForAdventure(bufferedReader);
                ArrayList<Item> borseta = creareBorseta();
                antrenor.primesteBorseta(borseta);
                antrenori.add(antrenor);
                preparePokemon(bufferedReader, antrenor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return antrenori;
    }

    private Antrenor getReadyAntrenorForAdventure(BufferedReader bufferedReader) throws IOException {
        Antrenor antrenor = readAntrenorForAdventure(bufferedReader);

        String listaPokemoni = bufferedReader.readLine();
        String[] numePokemon = listaPokemoni.split(",");

        for (String nume: numePokemon){
            Pokemon pokemon = obtainPokemon("src/main/java/Locatie/PokemonTypes.in", nume);
            antrenor.addPokemon(pokemon);
        }

        return antrenor;
    }

    private Antrenor readAntrenorForAdventure(BufferedReader bufferedReader) throws IOException {
        String nume = bufferedReader.readLine();
        int varsta = Integer.parseInt(bufferedReader.readLine());

        return new Antrenor(nume, varsta);
    }

    private Pokemon readPokemonFromReader (BufferedReader bufferedReader, String nume) throws IOException {
        int hp = Integer.parseInt(bufferedReader.readLine());
        int attack = interpretTypeOfAttack(bufferedReader.readLine());
        int specialAttack = interpretTypeOfAttack(bufferedReader.readLine());
        int defense = Integer.parseInt(bufferedReader.readLine());
        int specialDefense = Integer.parseInt(bufferedReader.readLine());

        return new Pokemon (nume, hp, attack, specialAttack, defense, specialDefense);
    }

    private SpecialAbility getAbilityFromReader (BufferedReader bufferedReader, String nume) throws IOException {
        int damage = Integer.parseInt(bufferedReader.readLine());
        boolean stun = interpretLine(bufferedReader.readLine());
        boolean dodge = interpretLine(bufferedReader.readLine());
        int coolDownTime = Integer.parseInt(bufferedReader.readLine());

        return new SpecialAbility(damage, stun, dodge, coolDownTime, nume);
    }

    /*
     * In fisier, un Pokemon primeste Obiectele speciale prin intermediul semnului "->", asa ca daca
     * impartim linia in 2, prima parte va fi numele Pokemonului, iar a 2-a parte vor fi numele Item-urilor.
     * Metoda primeste un reader cu care citeste din fisier Pokemonii si obiectele pe care le primesc acestia.
     */
    private void preparePokemon(BufferedReader bufferedReader, Antrenor antrenor) throws IOException {
        // Din problema stim ca avem 3 Pokemoni per Antrenor.
        for (int i = 0; i < 3; i++){
            String line = bufferedReader.readLine();
            String[] tokens = line.split("->");
            Pokemon pokemonCautat;

            // Daca Pokemonul nu primeste niciun obiect, trecem la urmatorul.
            if (tokens.length == 1)
                continue;

            pokemonCautat = antrenor.findPokemon(tokens[0]);
            if (pokemonCautat == null){
                System.out.println("Antrenorul nu are Pokemonul caruia vrei sa ii dai Obiectele " + tokens[1]);
                continue;
            }

            giveItemsToPokemon(tokens[1], antrenor, pokemonCautat);
        }
    }

    private void giveItemsToPokemon(String numeObiecte, Antrenor antrenor, Pokemon pokemon){
        String[] obiectSpecial = numeObiecte.split(",");
        List<Item> obiecteDisponibile = antrenor.getBorseta();

        for (String numeObiect: obiectSpecial){
            Item obiectCurent = findItem(numeObiect, obiecteDisponibile);

            if (obiectCurent != null)
                antrenor.giveItemToPokemon(pokemon, obiectCurent);
            else
                System.out.println("Nu exista tipul de obiect special " + numeObiect);
        }
    }

    private Item findItem(String nume, List<Item> items){
        for (Item item: items){
            if (item.isApplicable(nume))
                return item;
        }

        return null;
    }

    private boolean interpretLine (String isAction){
        return "Yes".equals(isAction);
    }

    private int interpretTypeOfAttack (String attack){
        if ("N/A".equals(attack))
            return 0;
        return Integer.parseInt(attack);
    }
}
