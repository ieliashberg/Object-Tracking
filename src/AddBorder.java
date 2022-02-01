import processing.core.PApplet;

public class AddBorder implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] border = new short[grid.length + 5][grid[0].length + 5];

        for (int i = 5; i < grid.length; i++) {
            for (int j = 5; j < grid[0].length; j++) {
                border[i][j] = grid[i][j];
            }
        }
        img.setPixels(border);
        return img;
    }
}
