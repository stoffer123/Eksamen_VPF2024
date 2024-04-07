package dk.cphbusiness.vp.f2024.Eksamen.demo;

import dk.cphbusiness.vp.f2024.Eksamen.impl.prototype.*;
import dk.cphbusiness.vp.f2024.Eksamen.intf.*;
import dk.cphbusiness.vp.f2024.Eksamen.textio.SystemTextIOImpl;
import dk.cphbusiness.vp.f2024.Eksamen.textio.TextIO;

import java.util.List;

public class ProtoDemo {
    public static void main(String[] args) {
        TextIO io = new SystemTextIOImpl();
        DungeonBuilder builder = new ProtoBuilder();
        DungeonDirector director = new ProtoDungeonDirector();
        Dungeon dungeon = director.constructDungeon(builder);
        ProtoEntity player = new ProtoEntity("Player");

        player.moveToLocation(dungeon.getLocations().get(0)); //Place player in start location

        while(true) {
            List<Portal> portals = player.getLocation().getPortals();

            player.moveToLocation(io.choose("Choose your next location", portals, "Choose: "));
            System.out.println(dungeon);
        }



    }
}
