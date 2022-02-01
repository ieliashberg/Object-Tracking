import processing.core.PApplet;

public class ColorMasking implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short redTarget = 173;
        short greenTarget = 57;
        short blueTarget = 68;
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                int redSquared = (red[r][c] - redTarget) * (red[r][c] - redTarget);
                int greenSquared = (green[r][c] - greenTarget) * (green[r][c] - greenTarget);
                int blueSquared = (blue[r][c] - blueTarget) * (blue[r][c] - blueTarget);
                int sum = redSquared + greenSquared + blueSquared;
                double distance = Math.sqrt(sum);
                if (distance < 50) {
                    red[r][c] = 255;
                    green[r][c] = 255;
                    blue[r][c] = 255;
                } else {
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                }
            }
        }
        img.setColorChannels(red, green, blue);
        return img;
    }
}

