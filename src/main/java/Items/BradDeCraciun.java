package Items;

import Pokemoni.Pokemon;

public class BradDeCraciun implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int attack = pokemon.getAttack();
        int defense = pokemon.getDefense();

        pokemon.setDefense(defense + 1);

        if (attack != 0)
            pokemon.setAttack(attack + 3);
        else
            System.out.println("Bradul de Craciun nu poate mari atacul Pokemon-ului " + pokemon.getNume());
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Brad de Craciun".equals(numeItem);
    }
}
