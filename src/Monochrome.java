import processing.core.PApplet;

public class Monochrome implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] > 127) {
                    grid[r][c] = 255;
                } else {
                    grid[r][c] = 0;
                }
            }
        }
        img.setPixels(grid);
        return img;
    }
}

