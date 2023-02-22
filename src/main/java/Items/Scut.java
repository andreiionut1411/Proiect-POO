package Items;

import Pokemoni.Pokemon;

public class Scut implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int defense = pokemon.getDefense();
        int specialDefense = pokemon.getSpecialDefense();

        pokemon.setDefense(defense + 2);
        pokemon.setSpecialDefense(specialDefense + 2);
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Scut".equals(numeItem);
    }
}
