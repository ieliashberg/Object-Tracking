import javax.swing.*;

public class PuzzleImage implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        DImage imgSmall = downSize(img);
        int imgWidth = imgSmall.getWidth();
        int imgHeight = imgSmall.getHeight();

        int width = imgWidth * (Integer.parseInt(JOptionPane.showInputDialog("Please input width")));
        int height = imgHeight * Integer.parseInt(JOptionPane.showInputDialog("Please input height"));

        DImage output = new DImage(width, height);
        short[][] redTiled = new short[height][width];
        short[][] greenTiled = new short[height][width];
        short[][] blueTiled = new short[height][width];

        short[][] red = imgSmall.getRedChannel();
        short[][] green = imgSmall.getGreenChannel();
        short[][] blue = imgSmall.getBlueChannel();

        for (int row = 0; row < height; row += imgHeight) {
            for (int col = 0; col < width; col += imgWidth) {
                for (int r = 0; r < red.length; r++) {
                    for (int c = 0; c < red[0].length; c++) {
                        redTiled[r + row][c + col] = red[r][c];
                        greenTiled[r + row][c + col] = green[r][c];
                        blueTiled[r + row][c + col] = blue[r][c];
                    }
                }
            }
        }

        output.setColorChannels(redTiled, greenTiled, blueTiled);
        return output;
    }

    private DImage downSize(DImage img){
        DImage out = new DImage(img.getWidth()/2, img.getHeight()/2);
        short[][] redBig = img.getRedChannel();
        short[][] greenBig = img.getGreenChannel();
        short[][] blueBig = img.getBlueChannel();

        short[][] redSmall = new short[redBig.length / 2][redBig[0].length / 2];
        short[][] greenSmall = new short[redBig.length / 2][redBig[0].length / 2];
        short[][] blueSmall = new short[redBig.length / 2][redBig[0].length / 2];

        for (int r = 0; r < redSmall.length; r++) {
            for (int c = 0; c < redSmall[0].length; c++) {
                redSmall[r][c] = redBig[r * 2][c * 2];
                greenSmall[r][c] = greenBig[r * 2][c * 2];
                blueSmall[r][c] = blueBig[r * 2][c * 2];
            }
        }
        out.setColorChannels(redSmall, greenSmall, blueSmall);
        return out;
    }
}
