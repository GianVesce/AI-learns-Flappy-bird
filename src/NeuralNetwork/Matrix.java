package NeuralNetwork;

import java.util.Arrays;

public class Matrix {
    int rows, cols;

    double[][] data;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];

        this.rows = rows;
        this.cols = cols;
    }

    private Matrix(){}

    public void randomize() {
        for (int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                data[i][j] = Math.random()*2 - 1;
            }
        }
    }

    public void applySigmoid() {
        for (int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                data[i][j] = 1/(1+Math.exp(-data[i][j]));
            }
        }
    }

    //Add with a scalar number
    public void add(double x) {
        for (int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                data[i][j] += x;
            }
        }
    }

    public void add(Matrix b) {
        if((rows == b.rows) && (cols == b.cols)) {
            for (int i=0; i<rows; i++) {
                for(int j=0; j<cols; j++) {
                    data[i][j] += b.data[i][j];
                }
            }
        }
    }

    //Multiply scalar
    public void multiply(double x) {
        for (int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                data[i][j] *= x;
            }
        }
    }

    public Matrix multiply(Matrix b) {
        Matrix a = this;
        if(a.cols != b.rows) {
            return null;
        }

        Matrix c = new Matrix(a.rows, b.cols);

        for (int i=0; i<c.rows; i++) {
            for(int j=0; j<c.cols; j++) {
                float sum = 0;
                for(int k=0; k<a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                c.data[i][j] = sum;
            }
        }

        return c;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        //System.out.println(a.cols + " " + b.rows);
        if(a.cols != b.rows) {
            return null;
        }

        Matrix c = new Matrix(a.rows, b.cols);

        for (int i=0; i<c.rows; i++) {
            for(int j=0; j<c.cols; j++) {
                float sum = 0;
                for(int k=0; k<a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                c.data[i][j] = sum;
            }
        }

        return c;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", data=\n" + Arrays.deepToString(data) +
                '}';
    }

    public double[] toArray() {
        double[] array = new double[cols];

        for (int i = 0; i < rows; i++) {
            array[i] = data[0][i];
        }

        return array;
    }

    public static Matrix fromArray(double[] array) {
        Matrix m = new Matrix(array.length, 1);

        for (int i = 0; i < array.length; i++) {
            m.data[i][0] = array[i];
        }

        return m;
    }

    public Matrix clone() {
        Matrix clone = new Matrix();
        clone.cols = cols;
        clone.rows = rows;
        clone.data = data.clone();

        return clone;
    }
}
