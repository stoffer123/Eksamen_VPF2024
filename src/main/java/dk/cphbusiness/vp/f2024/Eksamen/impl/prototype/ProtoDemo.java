package dk.cphbusiness.vp.f2024.Eksamen.impl.prototype;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Dungeon;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonBuilder;
import dk.cphbusiness.vp.f2024.Eksamen.intf.DungeonDirector;

public class ProtoDemo {
    public static void main(String[] args) {
        DungeonBuilder builder = new ProtoBuilder();
        DungeonDirector director = new ProtoDungeonDirector();
        Dungeon dungeon = director.constructDungeon(builder);


        System.out.println(dungeon);
    }
}
