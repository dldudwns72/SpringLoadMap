package hello.itemservice.domain.item;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.cleatStore();
    }

    @Test
    void save(){
        Item item = new Item("ItemA",10000,10);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        Item itemA = new Item("ItemA",10000,10);
        Item itemB = new Item("ItemB",20000,20);

       itemRepository.save(itemA);
         itemRepository.save(itemB);

        List<Item> result = itemRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(itemA,itemB);
    }

    @Test
    void updateItem(){
        Item item1 = new Item("item1",10000,10);

        Item savedItem = itemRepository.save(item1);

        Long itemId = savedItem.getId();

        Item updateParam = new Item("item2",20000,20);
        itemRepository.update(itemId,updateParam);

        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }


}