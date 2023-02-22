package Lupta;

import Pokemoni.*;

public class ThreadPtAntrenor extends Thread{
    private final Antrenor antrenorCurent;
    private final Antrenor antrenorAdvers;

    public ThreadPtAntrenor(Antrenor antrenorCurent, Antrenor antrenorAdvers) {
        this.antrenorCurent = antrenorCurent;
        this.antrenorAdvers = antrenorAdvers;
    }

    @Override
    public void run() {
        Pokemon opponentPokemon = antrenorAdvers.getCurrentPokemon();
        antrenorCurent.luptaPentruOTura(opponentPokemon, antrenorAdvers);
    }
}
