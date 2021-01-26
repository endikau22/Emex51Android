package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.Set;

@Root(name = "sectores")
public class Sectores {
    @ElementList(name = "sector", inline = true)
    private List<Sector> sectors;

    public List<Sector> getSectors() {
            return sectors;
    }
    public void setSectors (List<Sector> sectors) {
        this.sectors = sectors;
    }
}
