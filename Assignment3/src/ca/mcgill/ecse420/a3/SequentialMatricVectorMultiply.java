package ca.mcgill.ecse420.a3;

public class SequentialMatricVectorMultiply {

        public static double[] sequentialMultiply(double[][] matrix, double[] vector, int matrix_size) {
            double[] result = new double[matrix_size];
            for (int i=0;i<matrix_size;i++){       // interate through the matrix
                result[i] = 0;                     //store the entries of the matrix in the array
                for (int j=0;j<matrix_size;j++) {   //
                    result[i] += matrix[i][j] * vector[j]; // multiply the the matrix and the vector accordingly
                }
            }
            return result;      //return the final result

        }
    }

