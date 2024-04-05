package dk.cphbusiness.vp.f2024.Eksamen.intf;

import java.util.List;

public interface Location {
    String getName();
    String getDescription();
    List<Portal> getPortals();
    void addPortal(Portal portal);
}
