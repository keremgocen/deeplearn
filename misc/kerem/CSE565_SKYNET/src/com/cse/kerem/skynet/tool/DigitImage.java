package com.cse.kerem.skynet.tool;

/**
 * Created by keremgocen on 1/23/15.
 */
public class DigitImage {
    private int label;
    private byte[] imageData;

    public DigitImage(int label, byte[] imageData) {
        this.label = label;
        this.imageData = imageData;
    }
}
