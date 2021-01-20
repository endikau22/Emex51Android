package model;


import java.io.Serializable;

/**
 * Entity JPA class for Army data. This class inherits from de class
 * SectorContent. The property of this class is the ammunition.
 * @author Endika Ubierna, Markel Lopez de Uralde, Xabier Carnero
 * @version 1.0
 * @since 01/12/2020
 */

public class Army extends SectorContent implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The quantity of ammunition of the army.
     */
    private Integer ammunition;

    /**
     * Class constructor.
     */
    public Army() {
        super();
    }
    /**
     * Gets the quantity of ammunition of the army.
     * @return The ammunition value.
     */
    public int getAmmunition() {
        return ammunition;
    }
    /**
     * Sets the quantity of ammunition of the army.
     * @param ammunition The ammunition value.
     */
    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

}

