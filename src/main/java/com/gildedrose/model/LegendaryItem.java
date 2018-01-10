package com.gildedrose.model;

import javax.persistence.Entity;

@Entity
public class LegendaryItem extends Item {

    public LegendaryItem(){};

    public LegendaryItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality(){

    }

}
