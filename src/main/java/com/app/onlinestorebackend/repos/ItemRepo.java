package com.app.onlinestorebackend.repos;



import com.app.onlinestorebackend.entities.Item;
import com.app.onlinestorebackend.entities.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, String> {


    public Item findItemByIdIs(String id);

    public List<Item> findAllByItemStatusIs(ItemStatus itemStatus);

    public boolean existsByIdIs(String itemId);

    public int deleteItemByIdIs(String itemId);
}
