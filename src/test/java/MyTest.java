import Locatie.*;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyTest {

    /*
     * Se citeste alegerea tipului de arena din fisierul Tip_de_arena.in din resources. Primul numar
     * reprezinta numarul pentru testul numarul 5, al doilea numar pentru testul 6 si tot asa. Se
     * va alege o arena cu log in stdout daca se da un numar par, sau o arena cu log in fisier daca
     * se citeste din fisier un numar impar.
     */
    private Locatie alegeTipDeArena(int numarAventura){
        int varianta = 0;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/Tip_de_arena.in"))){
            int numarLinie = numarAventura - 4;
            for (int i = 0; i < numarLinie; i++){
                varianta = Integer.parseInt(bufferedReader.readLine());
            }
        }
        catch (IOException e){
            System.out.println("Probleme la citirea din fisier.");
        }

        if (varianta % 2 == 0)
            return new ArenaCuLogInStdout(new Arena("src/test/resources/in/Aventura" +
                    numarAventura + ".in"));
        else
            return new ArenaCuLogInFisier(new Arena("src/test/resources/in/Aventura" +
                    numarAventura + ".in"));
    }

    /*
     * Prima aventura are pentru un Pokemon un obiect atribuit de 3 ori si il adauga
     * o singura data in lista de obiecte.
     */
    @Test
    public void  aventura1(){
        Locatie arena = new ArenaCuLogInStdout(new Arena("src/test/resources/in/Aventura1.in"));
        arena.lupta();
    }

    /*
     * Pentru acest test un Pokemon nu a primit niciun item, dar acest
     * lucru nu influenteaza deloc rularea programului.
     */
    @Test
    public void aventura2(){
        Locatie arena = new ArenaCuLogInStdout(new Arena("src/test/resources/in/Aventura2.in"));
        arena.lupta();
    }

    // In acest test e o lupta absolut normala
    @Test
    public void aventura3(){
        Locatie arena = new ArenaCuLogInFisier(new Arena("src/test/resources/in/Aventura3.in"));
        arena.lupta();
    }

    /*
     * Pentru acest test am ales Pokemonii cu cea mai multa viata si le-am oferit item-uri
     * pentru a avea si mai multa viata. Acestia se lupta cu Pokemonii cu cea mai putina viata,
     * dar care au primit item-uri pentru a avea atacul cat mai mare. Este practic o lupta intre
     * viata si atac.
     */
    @Test
    public void aventura4(){
        Locatie arena = new ArenaCuLogInFisier(new Arena("src/test/resources/in/Aventura4.in"));
        arena.lupta();
    }

    // Incercam sa ii dam un item unui Pokemon pe care antrenorul nu il are.
    @Test
    public void aventura5(){
        Locatie arena = alegeTipDeArena(5);
        arena.lupta();
    }

    // Niciun Pokemon nu primeste item-uri
    @Test
    public void aventura6(){
        Locatie arena = alegeTipDeArena(6);
        arena.lupta();
    }

    // Ambii antrenori au aceiași antrenori in speranța ca poate apare o egalitate.
    @Test
    public void aventura7(){
        Locatie arena = alegeTipDeArena(7);
        arena.lupta();
    }

    // Pokemonii unuia dintre antrenori au primit item-uri, pe cand ceilalti nu.
    @Test
    public void aventura8(){
        Locatie arena = alegeTipDeArena(8);
        arena.lupta();
    }

    // Pokemonii si item-urile au fost alese absolut random.
    @Test
    public void aventura9(){
        Locatie arena = alegeTipDeArena(9);
        arena.lupta();
    }

    /*
     * Acesta fiind ultimul test are toate cazurile limita la un loc. Se incearca sa se ofere
     * un item unui Pokemon care nu exista, sunt item-uri lipsa, Pokemonii nu sunt in ordinea
     * din lista originala atunci cand le dam item-urile. I se ofera unui Pokemon acelasi item de
     * mai multe ori si mai sunt item-uri care nu au niciun efect asupra Pokemonului curent.
     */
    @Test
    public void aventura10(){
        Locatie arena = alegeTipDeArena(10);
        arena.lupta();
    }
}
