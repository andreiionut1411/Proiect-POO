package Pokemoni;

public class NormalAttack extends Attack{
    private final Pokemon pokemon;

    // Atacul normal poate fi folosit mereu, si nici nu da Stun sau Dodge.
    public NormalAttack(Pokemon pokemon) {
        super(numeAtac(pokemon), false, false);
       this.pokemon = pokemon;
    }

    // In functie daca Pokemon-ul are atac normal sau special, vom specifica in numele atacului.
    private static String numeAtac(Pokemon pokemon){
        if (pokemon.getAttack() == 0)
            return "atac special";

        return "atac normal";
    }

    public int getDamage(Pokemon pokemonAdvers){
        int damage;
        int attack = pokemon.getAttack();
        int specialAttack = pokemon.getSpecialAttack();

        if (attack != 0){
            int defense = pokemonAdvers.getDefense();
            damage = attack - defense;
        }
        else if (specialAttack != 0){
            int specialDefense = pokemonAdvers.getSpecialDefense();
            damage = specialAttack - specialDefense;
        }
        else
            return 0;

        return damage;
    }

    @Override
    public void executeAttack(Pokemon opponentPokemon, Antrenor antrenor) {
        // Daca oponentul are dodge, atunci nu il mai ranim.
        if (opponentPokemon.getStareCurenta() != opponentPokemon.getIsDodging()) {
            int damage = getDamage(opponentPokemon);
            ranestePokemon(opponentPokemon, damage);
        }
    }
}
