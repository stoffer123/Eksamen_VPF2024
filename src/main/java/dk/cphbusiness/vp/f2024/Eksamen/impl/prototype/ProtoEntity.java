package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Entity;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;

public class ProtoEntity implements Entity {
    private String name;
    private Location currentLocation;

    public ProtoEntity(String name) {
        this.name = name;
        this.currentLocation = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    @Override
    public void moveToLocation(Location location) {
        if(currentLocation == location) {
            return;
        }
        if(currentLocation != null) {
            ProtoLocation protoCurrentLocation = (ProtoLocation) currentLocation;
            protoCurrentLocation.removeEntity(this);

        }
        if(location == null) {
            currentLocation = null;
            return;
        }

        ProtoLocation protoNewLocation = (ProtoLocation) location;
        protoNewLocation.addEntity(this);
        currentLocation = location;
    }
}
