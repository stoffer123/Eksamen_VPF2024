package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

import javax.print.attribute.standard.Destination;

public class ProtoPortal implements Portal {
    private String name;
    private Location origin;
    private Location destination;

    public ProtoPortal(String name, Location origin, Location destination) {
        this.name = name;
        this.origin = origin;
        this.destination = destination;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getDestination() {
        return destination;
    }
}
