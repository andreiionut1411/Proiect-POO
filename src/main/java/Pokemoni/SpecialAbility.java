package Pokemoni;

public class SpecialAbility extends Attack{
    private final int damage;
    private final int coolDownTime;

    public SpecialAbility(int damage, boolean stun, boolean dodge, int coolDownTime, String nume) {
        super(nume, stun, dodge);
        this.damage = damage;
        this.coolDownTime = coolDownTime;
    }

    public int getDamage(Pokemon pokemon) {
        return damage;
    }

    public void isUsed(Antrenor antrenor){
        /*
         * Facem ca timeUntilIsAvailable sa fie coolDownTime + 1, deoarece reprezinta numarul
         * de ture care trebuie sa fie asteptat pana poate fi refolosita abilitatea, dar cand
         * se trece la urmatoarea tura se decrementeaza asteptarea, deci e ca si cum abilitatea
         * a asteptat deja o tura, in tura in care a fost folosita, ceea ce este fals. Daca nu
         * puneam +1, comanda ar fi putut sa fie data din nou cu o tura prea devreme.
         */
        setTimeUntilIsAvailable(coolDownTime + 1);
        antrenor.scoateComanda(this);
    }

    public void oneTurnPassed(Antrenor antrenor){
        if (getTimeUntilIsAvailable() != 0) {
            setTimeUntilIsAvailable(getTimeUntilIsAvailable() - 1);

            if (getTimeUntilIsAvailable() == 0){
                antrenor.adaugaComanda(this);
            }
        }
    }

    @Override
    public void executeAttack(Pokemon opponentPokemon, Antrenor antrenor) {
        /*
         * Daca oponentul are dodge nu il mai ranim, dar atacul oricum a fost incercat, deci
         * trece in cooldown.
         */
        if (opponentPokemon.getStareCurenta() != opponentPokemon.getIsDodging()) {
            ranestePokemon(opponentPokemon, damage);
        }
        isUsed(antrenor);
    }
}
