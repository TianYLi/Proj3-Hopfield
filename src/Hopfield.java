import java.io.*;

/**
 * Created by Jack Li on 10/31/2015.
 */
public class Hopfield {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to my disccrete hopfield neural network!\n");
        while(true) {
            int input = 0;
            System.out.println("Enter 1 to train using an image file. Enter 2 to test with a previously saved weight file: ");
            input = Integer.parseInt(br.readLine());
            if(input == 1) {
                System.out.println("Enter the training file name: ");
                String inputFile = br.readLine();
                inputFile = inputFile.concat(".txt");
                HopTrain(inputFile);
            }
            if(input == 2) {
                System.out.println("Enter the saved weights file name: ");
                String weightFile = br.readLine();
                weightFile = weightFile.concat(".txt");
                System.out.println("Enter the file you used to train your weights: ");
                String inputFile = br.readLine();
                inputFile = inputFile.concat(".txt");
                System.out.println("Enter the testing file name: ");
                String testFile = br.readLine();
                testFile = testFile.concat(".txt");
                System.out.println("Enter maximum number of iterations: ");
                String iterStr = br.readLine();
                int iter = Integer.parseInt(iterStr);
                HopTest(weightFile, inputFile, testFile, iter);
                System.exit(1);
            }
        }
    }
    private static void HopTrain(String inputFile) throws IOException {
        ImageData fData = fileParser(inputFile);

        int dim = fData.getDim();
        int numVecs = fData.getVec();
        int cols = fData.getCols();
        int rows = fData.getRows();
        int[][][] matrixArr = fData.getArr();

        System.out.println("dim: "+dim);
        System.out.println("num vecs: "+numVecs);
        int[][] weightMatrix = trainWeights(fData);


        FileWriter writer = new FileWriter("weights.txt");

        //writes first 2 numbers to file
        //writer.write(dim + "\n");
        //writer.write(numVecs + "\n");
        //writer.write("\n");
        //going through all weights and writing them
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                writer.write(weightMatrix[i][j] + ",");
            }
            writer.write("\n");
        }
        writer.close();
    }
    private static int[][] trainWeights(ImageData fData) throws IOException {
        int dim = fData.getDim();
        int numVecs = fData.getVec();
        int cols = fData.getCols();
        int rows = fData.getRows();
        int[][][] matrixArr = fData.getArr();
        int[][] weightMatrix = new int[cols][rows];
        for(int i = 0; i < numVecs; i++) {
            System.out.println("This is arr " + (i + 1) + ", which is size " + rows + "x" + cols);
            //each individual matrix set
            int[][] matrix = new int[rows][cols];
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    matrix[j][k] = matrixArr[i][j][k];
                    System.out.print(matrix[j][k]);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("transpose");

            //finds transpose of the matrix
            int[][] matrixTranspose = new int[cols][rows];
            for(int j = 0; j < cols; j++) {
                for(int k = 0; k < rows; k++) {
                    matrixTranspose[j][k] = matrix[k][j];
                    System.out.print(matrixTranspose[j][k]);
                }
                System.out.println();
            }
            System.out.println();

            //matrix multiplcation st*s
            System.out.println("weight");
            for(int j = 0; j < cols; j++) {
                for(int k = 0; k < rows; k++) {
                    int tempSum = 0;
                    for(int l = 0; l < rows; l++) {
                        tempSum += matrixTranspose[j][l]*matrix[l][k];
                    }
                    if(j != k) {
                        weightMatrix[j][k] += tempSum;
                    }
                    System.out.print(weightMatrix[j][k] + ", ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return weightMatrix;
    }
    private static ImageData fileParser(String inputFile) throws IOException {
        //reading files
        FileReader fread = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fread);

        //initializing and reading top 3 numbers
        int dim, numVectors;
        int rowLen;
        int colLen;
        dim =  Integer.parseInt(br.readLine());
        numVectors = Integer.parseInt(br.readLine());
        String line;
        int[][][] matrix;

        br.readLine(); //the blank line before each set of vectors
        //reads the first line to determine how many characters per line
        line = br.readLine();
        line = line.replace(" ", "0,").replace("O", "1,");
        String[] tempRow = line.split(",");
        //System.out.println(tempRow.length);
        int[] tempRow2 = new int[tempRow.length];
        for (int j = 0; j < tempRow.length; j++) {
            tempRow2[j] = Integer.parseInt(tempRow[j]);
            //System.out.print(tempRow[j]+", ");
            //System.out.print(tempRow2[j]);
        }
        //System.out.println();
        rowLen = tempRow.length;
        colLen = dim/tempRow.length;
        //System.out.println("Our matrix is "+rowLen+"x"+colLen);
        matrix = new int[numVectors][colLen][rowLen];
        matrix[0][0] = tempRow2;

        for(int j = 1; j < colLen; j++) {
            line = br.readLine();
            if(line.isEmpty()) {
                for(int k = 0; k < colLen; k++)
                    tempRow[k] = "0";
            }
            else
                tempRow = line.replace(" ", "0,").replace("O", "1,").split(",");
            //System.out.println(line);
            for (int k = 0; k < rowLen; k++) {
                matrix[0][j][k] = Integer.parseInt(tempRow[k]);
                //System.out.print(matrix[0][j][k]);
            }
            //System.out.println();
        }
        //System.out.println();
        //br.readLine(); //blank line

        for(int i = 1; i < numVectors; i++) {
            br.readLine(); //the blank line before each set of vectors
            for(int j = 0; j < colLen; j++) {
                line = br.readLine();
                if(line.isEmpty()) {
                    for(int k = 0; k < colLen; k++)
                        tempRow[k] = "0";
                }
                else
                    tempRow = line.replace(" ", "0,").replace("O", "1,").split(",");
                for (int k = 0; k < rowLen; k++) {
                    matrix[i][j][k] = Integer.parseInt(tempRow[k]);
                    //System.out.print(matrix[i][j][k]);
                }
                //System.out.println();
            }
            //System.out.println();
        }

        ImageData fData = new ImageData(dim, numVectors, matrix, rowLen, colLen);
        return fData;
    }
    private static void HopTest(String weightFile, String inputFile, String testFile, int maxIter) throws IOException {
        ImageData testData = fileParser(testFile);
        ImageData trainData = fileParser(inputFile);
        //reading files
        FileReader fread = new FileReader(weightFile);
        BufferedReader br = new BufferedReader(fread);
        int rows = testData.getRows();
        int cols = testData.getCols();
        int numVecs = testData.getVec();
        int[][][] testMatrix = testData.getArr();
        int[][][] trainMatrix = trainData.getArr();

        for(int i = 0; i < numVecs; i++) {
            for(int j = 0; j < rows; j++) {
                for(int k = 0; k < cols; k++) {
                    System.out.print(testMatrix[i][j][k]);
                }
                System.out.println();
            }
            System.out.println();
        }

        String line;
        int[][] weightMatrix = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            line = br.readLine();
            String[] temp = line.split(",");
            for(int j = 0; j < cols; j++) {
                weightMatrix[i][j] = Integer.parseInt(temp[j]);
                System.out.print(weightMatrix[i][j]+",");
            }
            System.out.println();
        }
        //gotta rewrite cuz used wrong algorithm!!!!
        for(int i = 0; i < numVecs; i++) {
            //for a given test pattern s
            // matrix multiplcation x*W
            int[][] matrixChecker = weightMatrix;
            boolean checker = false;
            int iter = 0;
            while(!checker && iter != maxIter) {
                for (int j = 0; j < cols; j++) {
                    for (int k = 0; k < rows; k++) {
                        int tempSum = 0;
                        for (int l = 0; l < rows; l++) {
                            tempSum += testMatrix[i][j][l] * matrixChecker[l][k];
                        }
                        if (tempSum > 1) {
                            matrixChecker[j][k] = 1;
                        }
                        if (tempSum == 0) {
                            matrixChecker[j][k] = 0;
                        }
                        if (tempSum < 1) {
                            matrixChecker[j][k] = -1;
                        }
                        System.out.print(matrixChecker[j][k] + ", ");
                    }
                    System.out.println();
                }
                System.out.println();
                boolean same = true;
                for (int v = 0; v < numVecs; v++) {
                    for (int j = 0; j < cols; j++) {
                        for (int k = 0; k < rows; k++) {
                            if (matrixChecker[j][k] != trainMatrix[v][j][k])
                                same = false;
                        }
                    }
                    if (same)
                        checker = true;
                }
                iter++;
            }
        }
    }

}
