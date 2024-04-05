package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;

import java.util.Collections;
import java.util.List;

public class ProtoDungeon implements Dungeon {

    private List<Location> locations;

    public ProtoDungeon(List<Location> locations) {
        this.locations = locations;
    }
    @Override
    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }
}
