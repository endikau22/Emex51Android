package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Set;

@Root(name = "sectores")
public class Sectores {
    @ElementList(name = "sector", inline = true)
    private Set<Sector> sectors;

    public Set<Sector> getSectors() {
            return sectors;
    }
    public void setSectors (Set<Sector> sectors) {
        this.sectors = sectors;
    }
}
