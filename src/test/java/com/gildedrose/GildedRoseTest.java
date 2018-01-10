package com.gildedrose;

import static org.junit.Assert.*;

import com.gildedrose.model.CheeseItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.LegendaryItem;
import com.gildedrose.model.TicketItem;
import com.gildedrose.GildedRose;
import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void qualityNeverNegative() throws Exception {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 3, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        app.updateQuality();
        assertEquals("+5 Dexterity Vest", app.items[0].name);
        assertEquals(0, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void qualityNeverGreaterThan50() throws Exception {
        Item[] items = new Item[] { new CheeseItem("Aged Brie", 5, 49) , new TicketItem("Backstage passes to a TAFKAL80ETC concert", 5, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(3, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);

        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[1].name);
        assertEquals(3, app.items[1].sellIn);
        assertEquals(50, app.items[1].quality);
    }

    @Test
    public void qualityDegrades_NormalItems() throws Exception {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("+5 Dexterity Vest", app.items[0].name);
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    @Test
    public void qualityDegradesTwiceAsFast_AfterSellDate_ButNeverNegative() throws Exception {
        Item[] items = new Item[] { new Item("+5 Dexterity Vest", -1, 1),
                new Item("+5 Dexterity Vest", -1, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("+5 Dexterity Vest", app.items[0].name);
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);

        assertEquals("+5 Dexterity Vest", app.items[1].name);
        assertEquals(-2, app.items[1].sellIn);
        assertEquals(0, app.items[1].quality);
    }

    @Test
    public void agedBrieQualityUpgradeX1_BeforeTheSellDate() throws Exception {
        Item[] items = new Item[] { new CheeseItem("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(1, app.items[0].sellIn);
        assertEquals(1, app.items[0].quality);
    }

    @Test
    public void agedBrieQualityUpgradeX2_AfterTheSellDate() throws Exception {
        Item[] items = new Item[] { new CheeseItem("Aged Brie", 0, 2),
                new CheeseItem("Aged Brie", 2, 49)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);

        assertEquals("Aged Brie", app.items[1].name);
        assertEquals(1, app.items[1].sellIn);
        assertEquals(50, app.items[1].quality);
    }

    @Test
    public void sulfurasSellDateAndQualityNeverUpgradeOrDegrades() throws Exception {
        Item[] items = new Item[] { new LegendaryItem("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
        assertEquals(0, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    public void backstageQualityUpgradeX1_WhenSellDateIsGreaterThanTen() throws Exception {
        Item[] items = new Item[] { new TicketItem("Backstage passes to a TAFKAL80ETC concert", 15, 20), };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(14, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    public void backstageQualityUpgradeX2_WhenSellDateIsLessEqualThanTen() throws Exception {
        Item[] items = new Item[] { new TicketItem("Backstage passes to a TAFKAL80ETC concert", 10, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(9, app.items[0].sellIn);
        assertEquals(12, app.items[0].quality);
    }

    @Test
    public void backstageQualityUpgradeX3_WhenSellDateIsLessEqualThanSix() throws Exception {
        Item[] items = new Item[] { new TicketItem("Backstage passes to a TAFKAL80ETC concert", 5, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(4, app.items[0].sellIn);
        assertEquals(13, app.items[0].quality);
    }

    @Test
    public void backstageQualityDegradesToZeroAfterSellDate(){
        Item[] items = new Item[] { new TicketItem("Backstage passes to a TAFKAL80ETC concert", 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

}
