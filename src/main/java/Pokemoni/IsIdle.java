package Pokemoni;

public class IsIdle extends State{

    public IsIdle(Pokemon pokemonCurent) {
        super(pokemonCurent);
    }

    @Override
    public void executaComanda(Attack command, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers) {
        Pokemon pokemonCurent = getPokemonCurent();
        boolean wasAttacked = pokemonCurent.isWasAttacked();

        if (!wasAttacked) {
            executa(command, opponentPokemon, antrenor);

            if (command.isDodge()) {
                State starePrecedenta = getPokemonCurent().getStareCurenta();
                pokemonCurent.setStareCurenta(pokemonCurent.getIsDodging());
                pokemonCurent.setStarePrecedenta(starePrecedenta);
            }
        }
        else {
            executaDupaCeAmFostAtacat(command, opponentPokemon, antrenor, antrenorAdvers);
        }

        opponentPokemon.setWasAttacked(true);
    }

    @Override
    public void nextTurn() {
        Pokemon pokemonCurent = getPokemonCurent();
        pokemonCurent.setStareCurenta(pokemonCurent.getIsIdle());
    }
}
