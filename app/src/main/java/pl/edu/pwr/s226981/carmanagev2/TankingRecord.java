package pl.edu.pwr.s226981.carmanagev2;

import java.io.Serializable;
import java.util.Date;

public class TankingRecord implements Serializable
{
    private Date tankUpDate;
    private Integer mileage, liters, cost;

    public TankingRecord(Date tankUpDate, Integer mileage, Integer liters, Integer cost) {
        this.tankUpDate = tankUpDate;
        this.mileage = mileage;
        this.liters = liters;
        this.cost = cost;
    }

    public Date getTankUpDate() {
        return tankUpDate;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Integer getLiters() {
        return liters;
    }

    /*
    public void setTankUpDate(Date tankUpDate) {
        this.tankUpDate = tankUpDate;
    }

    public Integer getCost() {
        return cost;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public void setLiters(Integer liters) {
        this.liters = liters;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
    */
}
