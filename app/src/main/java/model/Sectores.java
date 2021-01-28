package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Set;

/**
 * This class is used to receive a list of sectors
 */
@Root(name = "sectores")
public class Sectores {
    /**
     * A list of sectors
     */
    @ElementList(name = "sector", inline = true)
    private List<Sector> sectors;

    /**
     * Gets a list of sectors
     * @return A list of sectors.
     */
    public List<Sector> getSectors() {
            return sectors;
    }

    /**
     * Sets a list of sectors
     * @param sectors A list of sectors
     */
    public void setSectors (List<Sector> sectors) {
        this.sectors = sectors;
    }
}
