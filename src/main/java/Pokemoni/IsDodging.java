package Pokemoni;

public class IsDodging extends State{

    public IsDodging(Pokemon pokemonCurent) {
        super(pokemonCurent);
    }

    @Override
    public void executaComanda(Attack command, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers) {
        command.executeAttack(opponentPokemon, antrenor);
        opponentPokemon.setWasAttacked(true);
    }

    @Override
    public void nextTurn() {
        Pokemon pokemonCurent = getPokemonCurent();
        State starePrecedenta = pokemonCurent.getStareCurenta();
        pokemonCurent.setStareCurenta(pokemonCurent.getIsIdle());
        pokemonCurent.setStarePrecedenta(starePrecedenta);
    }
}
