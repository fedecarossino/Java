package com.gildedrose.model;

import javax.persistence.Entity;

@Entity
public class TicketItem extends Item {

    public TicketItem(){};

    public TicketItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality(){
        this.upgradeItemQuality();
        if (this.isSellInLessThan11()) {
            this.upgradeItemQuality();
        }
        if (this.isSellInLessThan6()) {
            this.upgradeItemQuality();
        }

        this.decreaseSellIn();

        if (this.isSellInLessThanZero()) {
            this.setQualityToZero();
        }
    }

    private boolean isSellInLessThan6() {
        return this.sellIn < 6;
    }

    private boolean isSellInLessThan11() {
        return this.sellIn < 11;
    }


}
