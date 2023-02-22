package Items;

import Pokemoni.Pokemon;

public class Pelerina implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int specialDefense = pokemon.getSpecialDefense();
        pokemon.setSpecialDefense(specialDefense + 3);
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Pelerina".equals(numeItem);
    }
}
