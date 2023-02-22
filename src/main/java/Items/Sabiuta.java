package Items;

import Pokemoni.Pokemon;

public class Sabiuta implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int attack = pokemon.getAttack();

        // Daca Pokemonul foloseste Special Attack, nu Attack, sabiuta este inutila.
        if (attack != 0)
            pokemon.setAttack(attack + 3);
        else
            System.out.println("Sabiuta nu are niciun efect asupra Pokemon-ului " + pokemon.getNume());
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Sabiuta".equals(numeItem);
    }
}
