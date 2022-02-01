import processing.core.PApplet;

import javax.swing.*;

public class MotionBlur implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = new short[grid.length][grid[0].length];
        String inputValue = JOptionPane.showInputDialog("Please choose a blur radius");
        int radius = Integer.parseInt(inputValue);
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                blurPixel(grid, newGrid, row, col, radius);
            }
        }
        img.setPixels(newGrid);
        return img;
    }

    public static void blurPixel(short[][] grid, short[][] newGrid, int row, int col, int radius) {
        int sum = 0;
        if (col + radius <= grid[0].length) {
            for (int i = col; i < col + radius; i++) {
                sum += grid[row][i];
            }
        } else {
            for (int i = col; i < grid[0].length-col; i++) {
                sum+= grid[row][i];
            }
        }
        if (col >= radius) {
            for (int i = col; i >= col - radius; i--) {
                sum += grid[row][i];
            }
        } else {
            for (int i = col; i >= 0; i--) {
                sum += grid[row][i];
            }
        }
        newGrid[row][col] = (short)(sum/(radius * 2));
    }
}

