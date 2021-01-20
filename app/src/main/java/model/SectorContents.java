package model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Set;
@Root(name = "sectorcontents")
public class SectorContents {
    @ElementList(name = "sectorcontent", inline = true)
    private Set<SectorContent> contentList;

    public Set<SectorContent> getContentList() {
        return contentList;
    }
    public void setContentList (Set<SectorContent> content) {
        this.contentList = content;
    }
}
