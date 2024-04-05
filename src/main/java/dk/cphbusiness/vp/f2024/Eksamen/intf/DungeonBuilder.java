package dk.cphbusiness.vp.f2024.Eksamen.intf;

public interface DungeonBuilder {
    Location createLocation(String name, String description);
    void createPortal(String name, Location origin, Location destination);
    Dungeon build();
}
