package com.gildedrose.model;

import javax.persistence.Entity;

@Entity
public class CheeseItem extends Item {

    public CheeseItem(){};

    public CheeseItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality(){
        this.decreaseSellIn();
        if (!this.isSellInLessThanZero()) {
            this.decreaseQuality();
        }
        this.upgradeItemQuality();
        this.upgradeItemQuality();
    }
}
