package com.gildedrose;

import com.gildedrose.model.Item;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        Arrays.stream(items).forEach(Item::updateQuality);

    }

}