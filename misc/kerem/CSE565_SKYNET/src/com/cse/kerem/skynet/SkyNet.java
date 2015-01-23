package com.cse.kerem.skynet;

import com.cse.kerem.skynet.tool.MNISTReader;

import com.cse.kerem.skynet.tool.DigitImage;
import com.cse.kerem.skynet.tool.DigitImageLoadingService;
import mnist.tools.MnistManager;

import java.io.IOException;
import java.util.List;

public class SkyNet {
    // Test class
    public static void main(String[] args) throws IOException {


        int numberOfInputs = 4;

        final String path = "/Users/keremgocen/Blesh/git-repos/deeplearn/MNIST";
        final String labelsPath = path + "/data0";
        final String imagesPath = path + "/train-images.idx3-ubyte.gz";

        // read MNIST data
        MNISTReader mnistReader = new MNISTReader(labelsPath, imagesPath);
        try {
            mnistReader.read(100);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        DigitImageLoadingService service = new DigitImageLoadingService(labelsPath, imagesPath);
//        try {
//            List<DigitImage> digitImageList = service.loadDigitImages();
//            System.out.println("digitImageList size:" + digitImageList.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        MnistManager mnistManager = new MnistManager(imagesPath, labelsPath);
//        System.out.println("label:" + mnistManager.readLabel());


//        NeuralNetwork nn = new NeuralNetwork(numberOfInputs, 0);
//        Layer l1 = new Layer(6, 0);
//        nn.addLayer(l1);
//        Layer l2 = new Layer(3, 0);
//        nn.addLayer(l2);
//
//        double[][] traindata = {{1, 0, 1, 0}, {0, 1, 0, 1}, {1, 1, 1, 1}};
//        double[][] trainlabels = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
//        nn.train(traindata, trainlabels);
//        double[] input = {1, 0, 1, 1};
//        double[] output = nn.run(input);
//
//        for (int i = 0; i < output.length; i++)
//            System.out.println(output[i]);

    }
}
