package com.cse.kerem.skynet.tool;

/**
* Created by keremgocen on 1/22/15.
*/

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MNISTReader {

    private static String labelsFileName;
    private static String imagesFileName;

    public MNISTReader(String labelsFileName, String imagesFileName) {
        this.labelsFileName = labelsFileName;
        this.imagesFileName = imagesFileName;
    }

    public static void read(int length) throws IOException {

        File labelsFile = new File(labelsFileName);
        FileInputStream labelsFileStream = new FileInputStream(labelsFile);
        byte [] labelsLength = new byte[(int)labelsFile.length()];
        labelsFileStream.read(labelsLength);
        ByteBuffer labelsByteBuffer = ByteBuffer.wrap(labelsLength);
        labelsByteBuffer.order(ByteOrder.BIG_ENDIAN);

        DataInputStream labels = new DataInputStream(new FileInputStream(labelsFileName));

//        File imagesFile = new File(imagesFileName);
//        FileInputStream imagesFileStream = new FileInputStream(imagesFile);
//        byte [] imagesLength = new byte[(int)imagesFile.length()];
//        imagesFileStream.read(imagesLength);
//        ByteBuffer imagesByteBuffer = ByteBuffer.wrap(imagesLength);
//        imagesByteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        DataInputStream images = new DataInputStream(new FileInputStream(imagesFileName));

        int magicInt = labelsByteBuffer.getInt();
        long magicAfter = (long)(magicInt & 0xFFFFFFFF);
        System.out.println("byte:" + magicInt + " hex:" + Integer.toHexString(magicInt) + " after:" + magicAfter + " hex:" + Long.toHexString(magicAfter));

        // first 2 bytes are 0
        while(labelsByteBuffer.hasRemaining()) {
            byte b = labelsByteBuffer.get();
            int after = (int)(b & 0xFF);
            System.out.println("byte:" + b + " hex:" + Integer.toHexString(b) + " after:" + after + " hex:" + Integer.toHexString(after));
        }

        labelsByteBuffer.get();

        int magicNumber = labels.readInt();
        System.out.println("magicNumber hex:" + Integer.toHexString(magicNumber) + " binary:" + Integer.toBinaryString(magicNumber));
        if (magicNumber != 2056) {
            System.err.println("Label file has wrong magic number: " + magicNumber + " (should be 2056)");
            System.exit(0);
        }
        magicNumber = images.readInt();
        if (magicNumber != 2056) {
            System.err.println("Image file has wrong magic number: " + magicNumber + " (should be 2056)");
            System.exit(0);
        }
        int numLabels = labels.readInt();
        int numImages = images.readInt();
        int numRows = images.readInt();
        int numCols = images.readInt();
        if (numLabels != numImages) {
            System.err.println("Image file and label file do not contain the same number of entries.");
            System.err.println("  Label file contains: " + numLabels);
            System.err.println("  Image file contains: " + numImages);
            System.err.println("  Image file rows: " + numRows);
            System.err.println("  Image file cols: " + numCols);
            System.err.println("  I will read this amount of samples : " + length);
            System.exit(0);
        } else {
            System.out.println("  Label file contains: " + numLabels);
            System.out.println("  Image file contains: " + numImages);
            System.out.println("  Image file rows: " + numRows);
            System.out.println("  Image file cols: " + numCols);
            System.out.println("  I will read this amount of samples : " + length);
        }

        long start = System.currentTimeMillis();
        int numLabelsRead = 0;
        int numImagesRead = 0;
        while (labels.available() > 0 && numLabelsRead < numLabels) {
            byte label = labels.readByte();
            numLabelsRead++;
            int[][] image = new int[numCols][numRows];
            for (int colIdx = 0; colIdx < numCols; colIdx++) {
                for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
                    image[colIdx][rowIdx] = images.readByte(); // masking higher order bits to get unsigned value
                }
            }
            numImagesRead++;

            // At this point, 'label' and 'image' agree and you can do whatever you like with them.

            if (numLabelsRead % 10 == 0) {
                System.out.print(".");
            }
            if ((numLabelsRead % 800) == 0) {
                System.out.print(" " + numLabelsRead + " / " + numLabels);
                long end = System.currentTimeMillis();
                long elapsed = end - start;
                long minutes = elapsed / (1000 * 60);
                long seconds = (elapsed / 1000) - (minutes * 60);
                System.out.println("  " + minutes + " m " + seconds + " s ");
            }
        }
        System.out.println();
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        long minutes = elapsed / (1000 * 60);
        long seconds = (elapsed / 1000) - (minutes * 60);
        System.out
                .println("Read " + numLabelsRead + " samples in " + minutes + " m " + seconds + " s ");
    }

    public static int toUnsignedInt(byte b) {
        int ret = 0;
        ret <<= 8;
        ret |= (int)b & 0xFF;
        return ret;
    }

}
