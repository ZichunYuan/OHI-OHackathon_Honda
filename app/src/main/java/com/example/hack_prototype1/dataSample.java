package com.example.hack_prototype1;

public class dataSample {
    private String Model;
    private int depRate;

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public int getDepRate() {
        return depRate;
    }

    public void setDepRate(int depRate) {
        this.depRate = depRate;
    }

    @Override
    public String toString() {
        return "dataSample{" +
                "Model='" + Model + '\'' +
                ", depRate=" + depRate +
                '}';
    }
}
