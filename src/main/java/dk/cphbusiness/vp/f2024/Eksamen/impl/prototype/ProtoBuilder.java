package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonBuilder;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

public class ProtoBuilder implements DungeonBuilder {
    @Override
    public Location createLocation(String name, String description) {
        Location location = new ProtoLocation(name, description);
        return location;
    }

    @Override
    public void createPortal(String name, Location origin, Location destination) {
        Portal portal = new ProtoPortal(name, origin, destination);
    }

    @Override
    public Dungeon build() {
        return null;
    }
}
