package com.gildedrose.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity()
@Table(name="item")
@Inheritance()
@DiscriminatorColumn(name="DISC", length=20)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="item")
public class Item {
    @Id
    @GeneratedValue
    protected long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "sell_in")
    public int sellIn;

    @Column(name = "quality")
    public int quality;

    public Item(){};

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

    public void updateQuality(){
        this.decreaseSellIn();
        this.degradeItemQuality();

        if (this.isSellInLessThanZero()) {
            this.setQualityToZero();
        }
    }

    protected void decreaseQuality(){
        this.quality--;
    }

    protected void increaseQuality(){
        this.quality++;
    }

    protected void decreaseSellIn(){
        this.sellIn--;
    }

    protected void degradeItemQuality() {
        if (this.isQualityGreaterThan0()) {
            decreaseQuality();
        }
    }

    protected boolean isSellInLessThanZero() {
        return this.sellIn < 0;
    }

    protected boolean isQualityLessThan50() {
        return this.quality < 50;
    }

    protected boolean isQualityGreaterThan0() {
        return this.quality > 0;
    }

    protected void upgradeItemQuality() {
        if (this.isQualityLessThan50()) {
            this.increaseQuality();
        }
    }

    protected void setQualityToZero(){
        this.quality = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Item getItem() {
        return this;
    }
}
