package com.gildedrose;

import com.gildedrose.model.CheeseItem;
import com.gildedrose.model.Item;
import com.gildedrose.model.LegendaryItem;
import com.gildedrose.model.TicketItem;
import com.gildedrose.repository.ItemsRepository;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;

@EnableJpaRepositories(basePackages = "com.gildedrose.repository")
@SpringBootApplication
public class JavaApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaApplication.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    public CommandLineRunner demoData(ItemsRepository repo) {
        return args -> {
            repo.save(new CheeseItem("Aged brie",10,2));
            repo.save(new LegendaryItem("Sulfuras",0,80));
            repo.save(new TicketItem("Entrada al Backstage",20,0));
            repo.save(new Item("Common",20,35));
            repo.save(new Item("Common2",25,47));
            repo.save(new CheeseItem("Aged brie2",11,20));
        };
    }
}
