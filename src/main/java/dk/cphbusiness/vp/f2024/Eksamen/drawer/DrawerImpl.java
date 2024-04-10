package dk.cphbusiness.vp.f2024.Eksamen.drawer;

public class DrawerImpl implements Drawer {
    private final char ulCorner = '\u250F';
    private final char urCorner = '\u2513';
    private final char llCorner = '\u2517';
    private final char lrCorner = '\u251B';
    private final char hLine = '\u2501';
    private final char vLine = '\u2503';

    @Override
    public char[][] drawBox(int width, int height) {
        char[][] box = new char[height][width];

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                box[y][x] = ' ';
                if(y == 0 && x == 0) {
                    box[y][x] = ulCorner;
                }
                else if(y == 0) {
                    box[y][x] = hLine;
                }
                if(y == 0 && x == width - 1) {
                    box[y][x] = urCorner;
                }
                else if((x == 0 || x == width - 1) && y != 0 && y != height - 1 ) {
                    box[y][x] = vLine;
                }
                if((y == height - 1 && x == 0)) {
                    box[y][x] = llCorner;
                } else if(y == height - 1 && x == width - 1) {
                    box[y][x] = lrCorner;
                }
                else if (y == height - 1 && (x != 0 && x != width - 1)) {
                    box[y][x] = hLine;
                }
            }
        }


        return box.clone();
    }
    @Override
    public void hardCodeBox() {




    }
}
