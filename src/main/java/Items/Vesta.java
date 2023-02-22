package Items;

import Pokemoni.Pokemon;

public class Vesta implements Item{
    @Override
    public void useItem(Pokemon pokemon) {
        int hp = pokemon.getHp();
        pokemon.setHp(hp + 10);
        pokemon.setHpCurent(hp + 10);
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Vesta".equals(numeItem);
    }
}
