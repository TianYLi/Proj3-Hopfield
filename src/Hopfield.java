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
                System.out.println("Enter the testing file name: ");
                String testFile = br.readLine();
                testFile = testFile.concat(".txt");
                HopTest(weightFile, testFile);
            }
            else
                System.out.println("invalid input, try again.");

        }
    }
    private static void HopTrain(String inputFile) throws IOException {
        //reading files
        FileInputStream fstream = new FileInputStream(inputFile);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //initializing and reading top 3 numbers
        int dim, numVectors;
        dim =  Integer.parseInt(br.readLine());
        numVectors = Integer.parseInt(br.readLine());
        String line;
        for(int i = 0; i < numVectors; i++) {
            br.readLine(); //the blank line before each set of vectors

            //reads the first line to determine how many characters per line
            line = br.readLine();
            String[] tempRow = line.replace(" ", "0,").replace("O", "1,").split(",");
            System.out.println(tempRow.length);
            int[] tempRow2 = new int[tempRow.length];
            for (int j = 0; j < tempRow.length; j++) {
                tempRow2[j] = Integer.parseInt(tempRow[j]);
                //System.out.print(tempRow[j]+", ");
                System.out.print(tempRow2[j]+", ");
            }
            System.out.println();
            int rowLen = tempRow.length;
            int colLen = dim/tempRow.length;
            System.out.println("Our matrix is "+rowLen+"x"+colLen);
            int[][] matrix = new int[colLen][rowLen];
            int matrixPos = 0;
            matrix[matrixPos] = tempRow2;
            matrixPos++;

            for(int j = 0; j < colLen-1; j++) {
                line = br.readLine();
                tempRow = line.replace(" ", "0,").replace("O", "1,").split(",");
                for (int k = 0; k < rowLen; j++) {
                    tempRow2[k] = Integer.parseInt(tempRow[k]);
                    //System.out.print(tempRow2[k]+", ");
                }
                System.out.println();
                matrix[matrixPos] = tempRow2;
                matrixPos++;
            }
            /*
            for(int j = 0; j < colLen; j++) {
                for(int k = 0; k < rowLen; k++) {
                    System.out.print(matrix[j][k]+", ");
                }
                System.out.println();
            }
            */
            //br.readLine(); //blank line
        }

    }
    private static void HopTest(String weightFile, String testFile) {

    }

}
