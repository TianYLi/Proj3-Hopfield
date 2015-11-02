/**
 * Created by Jack Li on 10/31/2015.
 */
public class ImageData {
    private int dim;
    private int num_vec;
    private int[][][] arrVec;
    private int arrRows;
    private int arrCols;

    public ImageData(int d, int v, int[][][] arr, int rows, int cols) {
        dim = d;
        num_vec = v;
        arrVec = arr;
        arrRows = rows;
        arrCols = cols;
    }
    public void setArr(int[][][] arr) {
        arrVec = arr;
    }
    public void setDim(int d) {
        dim = d;
    }
    public void setVec(int v) {
        num_vec = v;
    }
    public int[][][] getArr() {
        return arrVec;
    }
    public int getDim() {
        return dim;
    }
    public int getVec() {
        return num_vec;
    }
    public int getRows() {
        return arrRows;
    }
    public int getCols() {
        return arrCols;
    }
}
