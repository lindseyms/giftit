package com.lindsey.giftit.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public ItemDTO createNewItem(ItemDTO itemDTO){
        ItemEntity entity = new ItemEntity(itemDTO.getId(), itemDTO.getUsername(), itemDTO.getLink(), itemDTO.getTitle(), itemDTO.getDescription(), itemDTO.getPrice());
        entity = itemRepository.save(entity);
        return itemDTO;
    }

    public ItemDTO getItemById(Long id){
        ItemEntity entity = itemRepository.getOne(id);
        ItemDTO itemDTO = new ItemDTO(entity.getId(), entity.getUsername(), entity.getLink(), entity.getTitle(), entity.getDescription(), entity.getPrice());
        return itemDTO;
    }

    public List<ItemDTO> getAllItemsByUsername(String username){
        List<ItemEntity> entities = itemRepository.findAllItemsByUsername(username);
        List<ItemDTO> dtos = new ArrayList<>();
        for(ItemEntity e : entities){
            dtos.add(new ItemDTO(e.getId(), e.getUsername(), e.getLink(), e.getTitle(), e.getDescription(), e.getPrice()));
        }
        return dtos;
    }

    public void removeItem(Long id){
        itemRepository.deleteById(id);
    }
}
