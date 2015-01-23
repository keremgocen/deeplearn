package com.cse.kerem.skynet.backpropagation;

/**
 * Created by keremgocen on 11/14/14.
 * <p/>
 * Based on the algorithm on:http://www.cse.unsw.edu.au/~cs9417ml/MLP2/
 * <p/>
 * Assign all network inputs and output
 * Initialize all weights with small random numbers, typically between -1 and 1
 * <p/>
 * repeat
 * <p/>
 * for every pattern in the training set
 * <p/>
 * Present the pattern to the network
 * <p/>
 * //         Propagated the input forward through the network:
 * for each layer in the network
 * for every node in the layer
 * 1. Calculate the weight sum of the inputs to the node
 * 2. Add the threshold to the sum
 * 3. Calculate the activation for the node
 * end
 * end
 * <p/>
 * //         Propagate the errors backward through the network
 * for every node in the output layer
 * calculate the error signal
 * end
 * <p/>
 * for all hidden layers
 * for every node in the layer
 * 1. Calculate the node's signal error
 * 2. Update each node's weight in the network
 * end
 * end
 * //         Calculate Global Error
 * Calculate the Error Function
 * <p/>
 * end
 * <p/>
 * while ((maximum  number of iterations < than specified) AND
 * (Error Function is > than specified))
 */

public class Backpropagation {
    private static double threshold = 0.01;
    double[] input = new double[4];
    double[] output;
    double delta;
    double totalNextError = 0;
    double alpha = 0.001;
    private double errorRate = 1;
    private int epochCount = 0;

}
