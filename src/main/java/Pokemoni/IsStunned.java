package Pokemoni;

public class IsStunned extends State{

    public IsStunned(Pokemon pokemonCurent) {
        super(pokemonCurent);
    }

    @Override
    public void executaComanda(Attack command, Pokemon opponentPokemon, Antrenor antrenor, Antrenor antrenorAdvers) {
        Pokemon pokemonCurent = getPokemonCurent();
        boolean wasAttacked = pokemonCurent.isWasAttacked();

        // Daca Pokemonul este stunned, atunci nu se executa comanda.
        if (!wasAttacked){
            stareUrmatoareStunned(antrenor, pokemonCurent, pokemonCurent.getIsIdle());
        }
        else{
            stunnedDupaAtac(command, opponentPokemon, antrenor, antrenorAdvers, pokemonCurent);
        }

        opponentPokemon.setWasAttacked(true);
    }

    private void stunnedDupaAtac(Attack command, Pokemon opponentPokemon,
                                 Antrenor antrenor, Antrenor antrenorAdvers,
                                 Pokemon pokemonCurent) {
        /*
         * Daca Pokemonul este stunned, a fost atacat, dar atacul nu avea stun, inseamna
         * ca tura aceasta este tura in care trebuie sa nu faca nimic. Mai intai verificam
         * daca adversarul nu este stunned la randul lui.
         */
        if (antrenorAdvers.getComandaCurenta() != null) {
            if (!antrenorAdvers.getComandaCurenta().isStun()) {
                stareUrmatoareStunned(antrenor, pokemonCurent, pokemonCurent.getIsIdle());
            }

            /*
             * Daca comanda are stun, dar si starea precedenta, tot stun era, inseamna ca au fost date 2
             * stunuri unul dupa altul. Daca, starea precedenta nu e stun, inseamna ca tura aceasta poate ataca.
             */
            else {
                if (pokemonCurent.getStarePrecedenta() == pokemonCurent.getIsStunned()) {
                    stareUrmatoareStunned(antrenor, pokemonCurent, pokemonCurent.getIsStunned());
                } else {
                    executaDupaCeAmFostAtacat(command, opponentPokemon, antrenor, antrenorAdvers);
                }
            }
        }
        else{
            stareUrmatoareStunned(antrenor, pokemonCurent, pokemonCurent.getIsIdle());
        }
    }

    private void stareUrmatoareStunned(Antrenor antrenor, Pokemon pokemonCurent, State stareUrmatoare) {
        setareStari(pokemonCurent, stareUrmatoare);
        antrenor.commandIsStunned();
    }

    private void setareStari(Pokemon pokemonCurent, State stareCurenta) {
        State starePrecedenta = pokemonCurent.getStareCurenta();
        pokemonCurent.setStareCurenta(stareCurenta);
        pokemonCurent.setStarePrecedenta(starePrecedenta);
    }

    @Override
    public void nextTurn() {
        Pokemon pokemonCurent = getPokemonCurent();
        pokemonCurent.setStareCurenta(pokemonCurent.getIsStunned());
    }
}
