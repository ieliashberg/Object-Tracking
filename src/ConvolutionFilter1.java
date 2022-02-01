import processing.core.PApplet;

public class ConvolutionFilter1 implements PixelFilter {
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
    private double[][] horizontalEdges =
            {
                    {1, 2, 1},
                    {0, 0, 0},
                    {-1, -2, -1}};
    private double[][] verticalEdges =
            {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};

    @Override
    public DImage processImage(DImage img) {
        short[][] pixels = img.getBWPixelGrid();
        short[][] outputPixels = img.getBWPixelGrid();

        for (int row = 1; row < img.getHeight() - 1; row++) {
            for (int col = 1; col < img.getWidth() - 1; col++) {
                useKernel(pixels, outputPixels, row, col, verticalEdges);
                useKernel(pixels, outputPixels, row, col, horizontalEdges);
            }
        }

        img.setPixels(outputPixels);

        return img;
    }

    private void useKernel(short[][] pixels, short[][] outputPixels, int row, int col, double[][] kernel) {
        short newVal = findNewVal(pixels, row, col, kernel);
        if (newVal < 0) newVal = 0;
        if (newVal > 255) newVal = 255;

        outputPixels[row][col] = newVal;
        runThreshold(outputPixels);
    }

    private void runThreshold(short[][] output) {
        for (int row = 0; row < output.length; row++) {
            for (int col = 0; col < output[0].length; col++) {
                if (output[row][col] < 127) {
                    output[row][col] = 0;
                } else {
                    output[row][col] = 255;
                }
            }
        }
    }


    private short findNewVal(short[][] pixels, int row, int col, double[][] kernel) {
        double sum = 0;
        for (int r = row - 1; r < row + 2; r++) {
            for (int c = col - 1; c < col + 2; c++) {
                if (isInBounds(r, c, pixels)) {
                    sum += (pixels[r][c] * kernel[r - row + 1][c - col + 1]);
                }
            }
        }
        return (short) (sum);
    }

    private boolean isInBounds(int row, int col, short[][] arr) {
        return (row >= 0 && row < arr.length) && (col >= 0 && col < arr[0].length);
    }


    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}
