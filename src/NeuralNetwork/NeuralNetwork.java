package NeuralNetwork;

public class NeuralNetwork {
    private int inputCount;
    private int hiddenCount;
    private int outputCount;

    private Matrix ihWeights;
    private Matrix hoWeights;

    public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
        inputCount++;
        this.inputCount = inputCount;
        this.hiddenCount = hiddenCount;
        this.outputCount = outputCount;

        ihWeights = new Matrix(hiddenCount, inputCount);
        hoWeights = new Matrix(outputCount, hiddenCount);

        ihWeights.randomize();
        hoWeights.randomize();
    }

    private NeuralNetwork() {

    }

    public double[] feedforward(double[] inputs) {
        //Input + bias
        double[] inputBias = new double[inputs.length+1];
        for (int i = 0; i < inputs.length; i++) {
            inputBias[i] = inputs[i];
        }
        inputBias[inputs.length] = 1;

        Matrix inputMatrix = Matrix.fromArray(inputBias);
        //System.out.println(inputMatrix.toString());
        Matrix hiddenMatrix = Matrix.multiply(ihWeights, inputMatrix);
        //Activate the hidden nodes
        hiddenMatrix.applySigmoid();

        Matrix resultMatrix = Matrix.multiply(hoWeights, hiddenMatrix);
        //Activate the outputs
        resultMatrix.applySigmoid();

        double[] result = resultMatrix.toArray();

        return result;
    }

    public String toString() {
        return "inputCount: " + inputCount + " hiddenCount: " + hiddenCount + " outputCount:" + outputCount + "\nInput to hidden matrix: " + ihWeights.toString() + "\nHidden to output matrix:" + hoWeights.toString();
    }

    //Reproduce the neural network with a genetic algorithm
    public NeuralNetwork[] reproduce(int numberOfChildren, double changingRate) {
        NeuralNetwork[] children = new NeuralNetwork[numberOfChildren];

        //Make children
        for (int i = 0; i < children.length; i++) {
            //Create the children
            NeuralNetwork child = new NeuralNetwork();
            child.inputCount = inputCount;
            child.hiddenCount = outputCount;
            child.outputCount = outputCount;

            child.ihWeights = ihWeights.clone();
            child.hoWeights = hoWeights.clone();

            //Genetically modify the children
            Matrix ihWeightsError = new Matrix(ihWeights.rows, ihWeights.cols);
            ihWeightsError.randomize();
            ihWeightsError.multiply(changingRate);
            child.ihWeights.add(ihWeightsError);

            Matrix hoWeightsError = new Matrix(hoWeights.rows, hoWeights.cols);
            hoWeightsError.randomize();
            child.hoWeights.add(hoWeightsError);

            children[i] = child;
        }

        return children;
    }
}
