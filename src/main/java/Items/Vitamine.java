package Items;

import Pokemoni.Pokemon;

public class Vitamine implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int hp = pokemon.getHp();
        int attack = pokemon.getAttack();
        int specialAttack = pokemon.getSpecialAttack();

        pokemon.setHp(hp + 2);
        pokemon.setHpCurent(hp + 2);

        // Un Pokemon are Attack sau Special Attack, dar nu poate sa NU aiba niciunul.
        if (attack == 0 && specialAttack == 0)
            System.out.println("E ceva in neregula cu Pokemonul tau");
        else if (attack != 0)
            pokemon.setAttack(attack + 2);
        else
            pokemon.setSpecialAttack(specialAttack + 2);
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Vitamine".equals(numeItem);
    }
}
