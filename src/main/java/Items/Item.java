package Items;

import Pokemoni.Pokemon;

public interface Item {
    void useItem(Pokemon pokemon);
    boolean isApplicable(String numeItem);
}
