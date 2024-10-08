package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Az osztaly egy urhajo torpedoit tarolja es kezeli.
* Felelos a torpedok kiloveseert, szamontartasaert es a hibak szimulaciojaert.
*/
public class TorpedoStore {

  // A torpedok kilovesenek sikertelensegi aranya [0.0, 1.0]
  private double FAILURE_RATE = 0.0; //NOSONAR

  private int torpedoCount = 0;

  // A Random generator egy peldany valtozo, hogy elkeruljuk a tobbszori letrehozasat
  private Random generator = new Random();

  /**
  * A TorpedoStore konstruktora.
  * @param numberOfTorpedos a torpedok szama, amit a taroloba betoltunk.
  * A sikertelensegi aranyt frissiti, ha a megfelelo kornyezeti valtozo meg van adva.
  */
  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    // Frissiti a FAILURE_RATE erteket, ha a kornyezeti valtozoban meg van adva
    String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null){
      try {
        FAILURE_RATE = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        FAILURE_RATE = 0.0;
      }
    }
  }

  /**
  * A torpedok kiloveset vegzi el.
  * @param numberOfTorpedos a kilovendő torpedok szama
  * @return true, ha a kiloves sikeres, false, ha sikertelen.
  * Hibakezeles: IllegalArgumentException-t dob, ha a parameter erteke ervenytelen.
  */
  public boolean fire(int numberOfTorpedos){
    // Ellenorzi, hogy a torpedok szama ervenyes-e
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
      throw new IllegalArgumentException("Invalid number of torpedoes: " + numberOfTorpedos);
    }

    boolean success = false;

    // Veletlenszeruen szimulalja a kiloves meghiúsulasat
    double r = generator.nextDouble();

    if (r >= FAILURE_RATE) {
      // sikeres kiloves
      this.torpedoCount -= numberOfTorpedos; // A torpedok szama helyesen csokken
      success = true;
    } else {
      // szimulalt sikertelenseg
      success = false;
    }

    return success;
  }

  /**
  * Ellenorzi, hogy van-e meg torpedo.
  * @return true, ha nincs torpedo, false, ha van meg.
  */
  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  /**
  * Visszaadja az elerheto torpedok szamat.
  * @return a torpedok szama.
  */
  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}
