package pl.edu.pwr.s226981.carmanagev2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AutoInformation implements Serializable
{
    private String model, brand, color;
    private List<TankingRecord> tankingRecord = new ArrayList<>();

    public AutoInformation(String model, String brand, String color) {
        this.model = model;
        this.brand = brand;
        this.color = color;
    }

    @Override
    public String toString() {
        return brand + " " + model + " " + color;
    }

    public List<TankingRecord> getTankingRecord() {
        tankingRecord = new ArrayList<TankingRecord>();
        return tankingRecord;
    }

    public void setTankingRecord(List<TankingRecord> tankingRecord) {
        this.tankingRecord = tankingRecord;
    }
}
