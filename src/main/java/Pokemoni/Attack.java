package Pokemoni;

public abstract class Attack {
    private final String nume;
    private final boolean stun;
    private final boolean dodge;
    private int timeUntilIsAvailable;

    public Attack(String nume, boolean stun, boolean dodge) {
        this.nume = nume;
        this.stun = stun;
        this.dodge = dodge;
        this.timeUntilIsAvailable = 0;
    }

    public boolean isStun() {
        return stun;
    }

    public boolean isDodge() {
        return dodge;
    }

    public int getTimeUntilIsAvailable() {
        return timeUntilIsAvailable;
    }

    public String getNume() {
        return nume;
    }

    public String getCaracteristici(){
        String linie = nume;

        if (stun && dodge){
            linie += " (are dodge si stun)";
        }
        else if (stun){
            linie += " (are stun)";
        } else if (dodge) {
            linie += " (are dodge)";
        }

        return linie;
    }

    public void setTimeUntilIsAvailable(int timeUntilIsAvailable) {
        this.timeUntilIsAvailable = timeUntilIsAvailable;
    }

    public void resetTimeUntilIsAvailable(Antrenor antrenor){
        if (timeUntilIsAvailable != 0)
            antrenor.adaugaComanda(this);
        timeUntilIsAvailable = 0;
    }

    public void ranestePokemon(Pokemon opponentPokemon, int damage) {
        if (damage > 0) {
            int hpOpponent = opponentPokemon.getHpCurent();
            hpOpponent = hpOpponent - damage;
            opponentPokemon.setHpCurent(hpOpponent);
        }
    }

    public abstract void executeAttack(Pokemon opponentPokemon, Antrenor antrenor);
    public abstract int getDamage(Pokemon pokemon);
}
