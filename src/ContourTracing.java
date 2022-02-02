import core.DImage;

public class ContourTracing implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        img.setPixels(grid);
        return img;
    }
}

