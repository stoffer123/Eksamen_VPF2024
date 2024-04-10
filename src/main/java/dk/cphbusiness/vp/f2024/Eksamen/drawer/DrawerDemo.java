package dk.cphbusiness.vp.f2024.Eksamen.drawer;

public class DrawerDemo {
    public static void main(String[] args) {
        Drawer drawer = new DrawerImpl();

        char[][] box = drawer.drawBox(10,5);

        for(int i = 0; i < box.length; i++) {
            for(int j = 0; j < box[i].length; j++) {
                System.out.print(box[i][j]);
            }
            System.out.print(System.lineSeparator());
        }
    }
}
