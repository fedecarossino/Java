package com.gildedrose.api;

import com.gildedrose.api.exceptions.ItemBadRequestException;
import com.gildedrose.api.exceptions.ItemInternalErrorException;
import com.gildedrose.model.Item;
import com.gildedrose.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Validated
public class RestApiController {

    @Autowired
    ItemsRepository itemsRepository;

    @GetMapping("/items")
    public List<Item> get(HttpServletResponse response)
            throws IOException{
        try{
            return itemsRepository.findAll()
                    .stream()
                    .map(Item::getItem)
                    .collect(Collectors.toList());
        }catch(Exception e){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(HttpServletResponse response, @RequestBody Item item)
            throws IOException {
        try{
            if(item.quality < 0 || item.quality > 50 || item.name == null){
                throw new ItemBadRequestException();
            }
            return itemsRepository.save(item);

        }catch (ItemBadRequestException e){
            throw e;
        }catch(Exception e){
            throw new ItemInternalErrorException();
        }
    }

    @PatchMapping("/items/quality")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> updateAllItemsQuality(HttpServletResponse response, @RequestBody Map<String, Object> params)
            throws IOException {
        try {
            int quality = Integer.parseInt(params.get("quality").toString());
            if (quality < 0 || quality > 50) {
                throw new ItemBadRequestException();
            }
            Integer cant = itemsRepository.updateAllItemsQuality(quality);

            Map<String, Integer> responseMap = new HashMap<String, Integer>();
            responseMap.put("updated",cant);

            return responseMap;

        }catch (ItemBadRequestException e){
            throw e;
        }catch(Exception e){
            throw new ItemInternalErrorException();
        }
    }

}
