package com.lindsey.giftit.items;

import com.lindsey.giftit.users.UserEntity;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final MapperFacade mapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, MapperFacade mapper){
        this.itemRepository = itemRepository;
        this.mapper = mapper;
    }

    public ItemDTO createNewItem(ItemDTO itemDTO){
        UserEntity userEntity = loggedInUser();
        Long userId = userEntity.getId();
        ItemEntity entity = new ItemEntity(itemDTO.getId(), userId, itemDTO.getLink(), itemDTO.getDescription(), itemDTO.getPrice());
        itemRepository.save(entity);
        return itemDTO;
    }

    public ItemDTO getItemById(Long id){
        ItemEntity entity = itemRepository.getOne(id);
        ItemDTO itemDTO = mapper.map(entity, ItemDTO.class);
        return itemDTO;
    }

    public List<ItemDTO> findAllItemsByUserId(Long userId){
        List<ItemEntity> entities = itemRepository.findAllItemsByUserId(userId);
        List<ItemDTO> dtos = mapper.mapAsList(entities, ItemDTO.class);
        return dtos;
    }

    public void removeItem(String link, Long userId){
        itemRepository.deleteByLink(link, userId);
    }

    public UserEntity loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity)authentication.getPrincipal();
        return userEntity;
    }

}
