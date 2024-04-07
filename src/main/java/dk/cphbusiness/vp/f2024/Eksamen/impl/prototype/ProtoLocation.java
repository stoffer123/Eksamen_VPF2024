package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Entity;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProtoLocation implements Location {
    private String name;
    private String description;
    private List<Portal> portals;
    private List<Entity> entities;

    public ProtoLocation(String name, String description) {
        this.name = name;
        this.description = description;
        this.portals = new ArrayList<>();
        this.entities = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<Portal> getPortals() {
        return Collections.unmodifiableList(portals);
    }

    @Override
    public void addPortal(Portal portal) {
        portals.add(portal);

    }

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    @Override
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
}
