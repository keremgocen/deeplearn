package com.cse.kerem.skynet.tool;

/**
* Created by keremgocen on 1/22/15.
*/

/*
TRAINING SET LABEL FILE (train-labels-idx1-ubyte):

[offset] [type]          [value]          [description]
0000     32 bit integer  0x00000801(2049) magic number (MSB first)
0004     32 bit integer  60000            number of items
0008     unsigned byte   ??               label
0009     unsigned byte   ??               label
........
xxxx     unsigned byte   ??               label
The labels values are 0 to 9.

TRAINING SET IMAGE FILE (train-images-idx3-ubyte):

[offset] [type]          [value]          [description]
0000     32 bit integer  0x00000803(2051) magic number
0004     32 bit integer  60000            number of images
0008     32 bit integer  28               number of rows
0012     32 bit integer  28               number of columns
0016     unsigned byte   ??               pixel
0017     unsigned byte   ??               pixel
........
xxxx     unsigned byte   ??               pixel
Pixels are organized row-wise. Pixel values are 0 to 255. 0 means background (white), 255 means foreground (black).
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

        // TODO implement "length" option

        File labelsFile = new File(labelsFileName);
        FileInputStream labelsFileStream = new FileInputStream(labelsFile);
        byte [] labelsLength = new byte[(int)labelsFile.length()];
        labelsFileStream.read(labelsLength);
        ByteBuffer labelsByteBuffer = ByteBuffer.wrap(labelsLength);
        labelsByteBuffer.order(ByteOrder.BIG_ENDIAN);

//        DataInputStream labels = new DataInputStream(new FileInputStream(labelsFileName));

        File imagesFile = new File(imagesFileName);
        FileInputStream imagesFileStream = new FileInputStream(imagesFile);
        byte [] imagesLength = new byte[(int)imagesFile.length()];
        imagesFileStream.read(imagesLength);
        ByteBuffer imagesByteBuffer = ByteBuffer.wrap(imagesLength);
        imagesByteBuffer.order(ByteOrder.BIG_ENDIAN);

//        DataInputStream images = new DataInputStream(new FileInputStream(imagesFileName));

        int magicInt = labelsByteBuffer.getInt();
        long magicAfter = (long)(magicInt & 0xFFFFFFFF);
        System.out.println("byte:" + magicInt + " hex:" + Integer.toHexString(magicInt) + " after:" + magicAfter + " hex:" + Long.toHexString(magicAfter));


        // debug
        while(labelsByteBuffer.hasRemaining()) {
            byte b = labelsByteBuffer.get();
            int after = (int)(b & 0xFF);
            System.out.println("byte:" + b + " hex:" + Integer.toHexString(b) + " after:" + after + " hex:" + Integer.toHexString(after));
        }

        labelsByteBuffer.reset();

        int magicNumber = labelsByteBuffer.getInt();
        System.out.println("magicNumber hex:" + Integer.toHexString(magicNumber) + " binary:" + Integer.toBinaryString(magicNumber));
        if (magicNumber != 2049) {
            System.err.println("Label file has wrong magic number: " + magicNumber + " (should be 2049)");
            System.exit(0);
        }
        magicNumber = imagesByteBuffer.getInt();
        if (magicNumber != 2051) {
            System.err.println("Image file has wrong magic number: " + magicNumber + " (should be 2051)");
            System.exit(0);
        }
        int numLabels = labelsByteBuffer.getInt();
        int numImages = imagesByteBuffer.getInt();
        int numRows = imagesByteBuffer.getInt();
        int numCols = imagesByteBuffer.getInt();
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
        while (labelsByteBuffer.remaining() > 0 && numLabelsRead < numLabels) {
            byte label = labelsByteBuffer.get();
            numLabelsRead++;
            int[][] image = new int[numCols][numRows];
            for (int colIdx = 0; colIdx < numCols; colIdx++) {
                for (int rowIdx = 0; rowIdx < numRows; rowIdx++) {
                    image[colIdx][rowIdx] = imagesByteBuffer.get();
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
