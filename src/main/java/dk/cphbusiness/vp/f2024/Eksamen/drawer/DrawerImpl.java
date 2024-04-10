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

                //Horizontal lines
                if((x != 0 && x != width - 1 ) && (y == 0 || y == height - 1)) {
                    box[y][x] = hLine;
                }

                //Vertical lines
                if((x == 0 || x == width - 1) && (y != 0 && y != height - 1)) {
                    box[y][x] = vLine;
                }


            }
        }
        //Corners
        box[0][0] = ulCorner;
        box[0][width - 1] = urCorner;
        box[height -1][0] = llCorner;
        box[height - 1][width - 1] = lrCorner;


        return box.clone();
    }
    @Override
    public void hardCodeBox() {




    }
}
