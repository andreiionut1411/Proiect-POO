package Pokemoni;

public abstract class State {
    private final Pokemon pokemonCurent;

    public State(Pokemon pokemonCurent) {
        this.pokemonCurent = pokemonCurent;
    }

    public Pokemon getPokemonCurent() {
        return pokemonCurent;
    }

    public synchronized static void comandaInFctDeStare(Attack command,
                                                        Pokemon currentPokemon,
                                                        Pokemon opponentPokemon,
                                                        Antrenor antrenorCurent,
                                                        Antrenor antrenorAdvers){
        State stare = currentPokemon.getStareCurenta();
        stare.executaComanda(command, opponentPokemon, antrenorCurent, antrenorAdvers);
    }

    public void vindecaPokemon(Pokemon pokemonCurent, int damage){
        if (damage > 0){
            int hpCurent = pokemonCurent.getHpCurent();
            hpCurent += damage;
            pokemonCurent.setHpCurent(hpCurent);
        }
    }

    public void undoAttack(Pokemon pokemonCurent, Antrenor antrenorAdvers) {
        Attack comanda = antrenorAdvers.getComandaCurenta();

        // Verificam daca nu cumva adversarul pur si simplu era stunned.
        if (comanda != null) {
            int damage = comanda.getDamage(pokemonCurent);
            vindecaPokemon(pokemonCurent, damage);
        }
    }

    public void executaDupaCeAmFostAtacat(Attack command, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers) {
        executa(command, opponentPokemon, antrenor);

        // Daca Pokemonul a fost atacat, desi comanda are dodge, atunci trebuie sa primeasca viata inapoi.
        if (command.isDodge()) {
            State starePrecedenta = pokemonCurent.getStareCurenta();
            undoAttack(pokemonCurent, antrenorAdvers);
            pokemonCurent.setStareCurenta(pokemonCurent.getIsDodging());
            pokemonCurent.setStarePrecedenta(starePrecedenta);
        }
    }

    public void executa(Attack command, Pokemon opponentPokemon, Antrenor antrenor) {
        command.executeAttack(opponentPokemon, antrenor);
        incercStun(command, opponentPokemon);
    }

    public void incercStun(Attack command, Pokemon opponentPokemon) {
        // Daca comanda mea are stun si adversarul nu se fereste, atunci ii dau stun.
        if (command.isStun() && opponentPokemon.getStareCurenta() != opponentPokemon.getIsDodging()) {
            State starePrecedenta = opponentPokemon.getStareCurenta();
            opponentPokemon.setStareCurenta(opponentPokemon.getIsStunned());
            opponentPokemon.setStarePrecedenta(starePrecedenta);
        }
    }

    public abstract void executaComanda(Attack command, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers);
    public abstract void nextTurn();
}
