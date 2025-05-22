package com.saffat.examconductor;

public class MainModel {
    Integer numImage;
    String numWord;

    public MainModel(Integer numImage, String numWord) {
        this.numImage = numImage;
        this.numWord = numWord;
    }

    public Integer getNumImage() {
        return numImage;
    }

    public void setNumImage(Integer numImage) {
        this.numImage = numImage;
    }

    public String getNumWord() {
        return numWord;
    }

    public void setNumWord(String numWord) {
        this.numWord = numWord;
    }
}
