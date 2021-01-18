package model;


import java.io.Serializable;

/**
 * Entity JPA class for Creature data. This class inherits from de class
 * SectorContent. The property of this class is species.
 *
 * @author Xabier Carnero, Endika Ubierna, Markel Lopez de Uralde.
 * @version 1.0
 * @since 01/12/2020
 */

public class Creature extends SectorContent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The species of the creature.
     */
    private String species;

    /**
     * Class constructor.
     */
    public Creature() {
        super();
    }

    /**
     * Gets the species of the creature
     * @return The species value.
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Sets the species of the creature
     * @param species The species value.
     */
    public void setSpecies(String species) {
        this.species = species;
    }
}

