package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonBuilder;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonDirector;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;

public class ProtoDungeonDirector implements DungeonDirector {
    @Override
    public Dungeon constructDungeon(DungeonBuilder builder) {
        Location l1 = builder.createLocation("L1", "Location 1");
        Location l2 = builder.createLocation("L2", "Location 2");
        Location l3 = builder.createLocation("L3", "Location 3");
        Location l4 = builder.createLocation("L4", "Location 4");
        builder.createPortal("P1", l1, l2);
        builder.createPortal("P2", l2, l3);
        builder.createPortal("P3", l3, l4);
        builder.createPortal("P4", l4, l1);
        return builder.build();
    }
}
