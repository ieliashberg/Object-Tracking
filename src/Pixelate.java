import processing.core.PApplet;

import javax.swing.*;

public class Pixelate implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();
        String inputValue = JOptionPane.showInputDialog("Please input block size value (odd number only)");
        int size = Integer.parseInt(inputValue);
        for (int row = 0; row < grid.length - size; row += size) {
            for (int col = 0; col < grid[0].length - size; col += size) {
                pixelateGrid(grid, row, col, size,size);
                if (grid.length / size != 0 || grid[0].length / size != 0) {
                    finishPixelating(grid, size);
                }
            }
        }
        img.setPixels(grid);
        return img;
    }

    private void finishPixelating(short[][] grid, int size) {
        int oldHeight = (grid.length-1 / size);
        for (int j = 0; j < grid[0].length - oldHeight; j += oldHeight) {
            pixelateGrid(grid, grid.length - oldHeight, j, oldHeight, size);
        }
        int newHeight = grid[0].length / size;
        for (int i = grid.length; i < grid.length - oldHeight; i+=newHeight) {
            pixelateGrid(grid, i, grid[0].length - newHeight, newHeight,size);
        }
    }


    public static void pixelateGrid(short[][] grid, int row, int col, int height, int width) {
        for (int i = row; i < row + height; i++) {
            for (int j = col; j < col + width; j++) {
                grid[i][j] = grid[row + (height / 2) + 1][col + (width / 2) + 1];
            }
        }
    }
}

