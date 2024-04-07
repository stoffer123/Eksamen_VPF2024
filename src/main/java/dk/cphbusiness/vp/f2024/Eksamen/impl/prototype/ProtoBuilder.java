package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonBuilder;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

import java.util.ArrayList;
import java.util.List;

public class ProtoBuilder implements DungeonBuilder {
    private final List<Location> allLocations;

    public ProtoBuilder() {
        this.allLocations = new ArrayList<>();
    }


    @Override
    public Location createLocation(String name, String description) {
        Location location = new ProtoLocation(name, description);
        allLocations.add(location);
        return location;
    }

    @Override
    public void createPortal(String name, Location origin, Location destination) {
        if(!(origin instanceof ProtoLocation)) {

            throw new IllegalArgumentException("Only use locations from the same dungeon builder!");

        }
        ProtoLocation originLocation = (ProtoLocation) origin;
        Portal portal = new ProtoPortal(name, origin, destination);
        originLocation.addPortal(portal);
    }

    @Override
    public Dungeon build() {
        return new ProtoDungeon(allLocations);
    }
}
