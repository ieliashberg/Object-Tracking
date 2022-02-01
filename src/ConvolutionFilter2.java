import processing.core.PApplet;

//Ilan Eliashberg (this is my edit)
public class ConvolutionFilter2 implements PixelFilter {
    private double[][] blurKernel =
            {{1.0 / 9, 1.0 / 9, 1.0 / 9},
                    {1.0 / 9, 1.0 / 9, 1.0 / 9},
                    {1.0 / 9, 1.0 / 9, 1.0 / 9}};

    private double[][] outlineKernel =
            {
                    {-1, -1, -1},
                    {-1, 8, -1},
                    {-1, -1, -1}};

    private double[][] embossKernel =
            {
                    {-2, -1, 0},
                    {-1, 1, 1},
                    {0, 1, 2}};
    private double[][] sobelX =
            {
                    {1, 2, 1},
                    {0, 0, 0},
                    {-1, -2, -1}};
    private double[][] sobelY =
            {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};
    private int[][] rightThinning =
            {
                    {3, 0, 0},
                    {1, 1, 0},
                    {3, 1, 3}};
    private int[][] leftThinning =
            {
                    {0, 0, 0},
                    {3, 1, 3},
                    {1, 1, 1}};

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();

        for (int row = 1; row < img.getHeight() - 1; row++) {
            for (int col = 1; col < img.getWidth() - 1; col++) {
                sobelEdgeDetection(row, col, outputPixels, pixels);
            }
        }
        runThreshold(outputPixels, 127);
        thinning(outputPixels);
        img.setPixels(outputPixels);
        return img;
    }

    private void sobelEdgeDetection(int row, int col, short[][] outputPixels, short[][] pixels) {
        double sumX = findSum(pixels, row, col, sobelX);
        double sumY = findSum(pixels, row, col, sobelY);
        short newVal = (short) (Math.sqrt(sumX * sumX + sumY * sumY));

        outputPixels[row][col] = newVal;
    }

    private void thinning(short[][] output) {

        for (int row = 1; row < output.length - 1; row++) {
            for (int col = 1; col < output[0].length - 1; col++) {
                for (int i = 0; i < 4; i++) {
                    compareKernels(row, col, output, leftThinning);
                    compareKernels(row, col, output, rightThinning);

                    rotateKernel(leftThinning);
                    rotateKernel(rightThinning);
                }
            }
        }
    }

    private void compareKernels(int row, int col, short[][] output, int[][] kernel) {
        boolean match = true;
        for (int r = row - 1; r < row + 2; r++) {
            for (int c = col - 1; c < col + 2; c++) {
                match = match &&
                        ((kernel[r - row + 1][c - col + 1] == 3.0) ||
                                ((double) (output[r][c] / 255) == kernel[r - row + 1][c - col + 1]));
            }
        }
        if (match) {
            output[row][col] = 0;
        } else {
            output[row][col] = 255;
        }
    }

    private int[][] rotateKernel(int[][] kernel) {
        int[][] outputKernel = new int[kernel.length][kernel[0].length];
        for (int r = 0; r < kernel.length; r++) {
            for (int c = 0; c < kernel[0].length; c++) {
                outputKernel[c][(kernel.length - 1) - r] = kernel[r][c];
            }
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                (kernel[i][j]) = outputKernel[i][j];
            }

        }
        return outputKernel;
    }

    private void runThreshold(short[][] output, int threshold) {
        for (int row = 0; row < output.length; row++) {
            for (int col = 0; col < output[0].length; col++) {
                if (output[row][col] < threshold) {
                    output[row][col] = 0;
                } else {
                    output[row][col] = 255;
                }
            }
        }
    }

    private double findSum(short[][] pixels, int row, int col, double[][] kernel) {
        double sum = 0;
        for (int r = row - 1; r < row + 2; r++) {
            for (int c = col - 1; c < col + 2; c++) {
                if (isInBounds(r, c, pixels)) {
                    sum += (pixels[r][c] * kernel[r - row + 1][c - col + 1]);
                }
            }
        }
        return (sum);
    }

    private boolean isInBounds(int row, int col, short[][] arr) {
        return (row >= 0 && row < arr.length) && (col >= 0 && col < arr[0].length);
    }
}
