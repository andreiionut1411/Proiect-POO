package Items;

import Pokemoni.Pokemon;

public class BaghetaMagica implements Item {
    @Override
    public void useItem(Pokemon pokemon) {
        int specialAttack = pokemon.getSpecialAttack();

        if (specialAttack != 0)
            pokemon.setSpecialAttack(specialAttack + 3);
        else
            System.out.println("Bagheta magica nu are niciun efect asupra Pokemon-ului " + pokemon.getNume());
    }

    @Override
    public boolean isApplicable(String numeItem) {
        return "Bagheta Magica".equals(numeItem);
    }
}
