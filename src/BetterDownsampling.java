import processing.core.PApplet;

public class BetterDownsampling implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = new short[grid.length/2][grid[0].length/2];
        for (int i = 0; i < grid.length-1; i+=2) {
            for (int j = 0; j < grid[0].length-1; j+=2) {
                short avgVal = findAvgVal(getTwoByTwo(grid, i, j));
                newGrid[(short)(i/2)][(short)(j/2)] = avgVal;
            }

        }
        img.setPixels(newGrid);
        return img;
    }

    private short findAvgVal(short[][] twoByTwo) {
        short sum = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                sum+=twoByTwo[i][j];
            }
        }
        return (short)(sum/4);
    }

    private short[][] getTwoByTwo(short[][] grid, int row, int col){
        short[][] twoByTwo = new short[2][2];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                twoByTwo[i][j] = grid[row+i][col+j];
            }
        }
        return twoByTwo;
    }
}
