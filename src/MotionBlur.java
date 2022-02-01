import processing.core.PApplet;

import javax.swing.*;

public class MotionBlur implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        short[][] newGrid = new short[grid.length][grid[0].length];
        String inputValue = JOptionPane.showInputDialog("Please choose a blur radius");
        int radius = Integer.parseInt(inputValue);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (c+radius > grid[0].length-1){
                    smallBlur(grid, newGrid, r, c, radius);
                } else {
                    blur(grid, newGrid, r, c, radius);
                }
            }
        }

        img.setPixels(newGrid);
        return img;
    }
    private void smallBlur(short[][] grid, short[][] newGrid, int r, int c, int radius) {
        radius = grid[0].length - c;
        short average = 0;
        for (short i = 0; i < radius; i++) {
            average += grid[r][c+i];
        }

        newGrid[r][c] = (short) (average / radius);
    }

    private void blur(short[][] grid, short[][] newGrid, int r, int c, int radius){
        short average = 0;
        for (short i = 0; i < radius; i++) {
            average += grid[r][c+i];
        }

        newGrid[r][c] = (short) (average / radius);
    }
}

