package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Entity;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

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

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(Location location : locations)
        {
            res.append(location.getName()).append(System.lineSeparator());
            res.append("    Portals:").append(System.lineSeparator());
            for(Portal portal : location.getPortals())
            {
                res.append("    ")
                        .append(portal.getName())
                        .append(" -> ")
                        .append(portal.getDestination().getName())
                        .append(System.lineSeparator());
            }


            res.append("    Entities:").append(System.lineSeparator());

            for(Entity entity : location.getEntities())
            {
                res.append("    ")
                        .append(entity.getName())
                        .append(System.lineSeparator());
            }



        }
        return res.toString();
    }
}
