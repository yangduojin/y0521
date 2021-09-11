package com.atguigu;


import com.atguigu.dao.ItemRepository;
import com.atguigu.pojo.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;
    
    @Test
    public void testCreate(){
        elasticsearchTemplate.createIndex(Item.class);
        elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testAdd(){
        Item item = new Item(1L, "华为手机", "手机", "华为", 2200.00);
        itemRepository.save(item);
    }

    @Test
    public void testUpdate(){
        Item item = new Item(1L, "小米手机", "手机", "小米", 2400.00);
        itemRepository.save(item);
    }

    @Test
    public void testIndexList(){
        List<Item> list = new ArrayList<>();
        list.add(new Item(5L, "xxx手机", "手机", "华为", 2200.00));
        list.add(new Item(3L, "yyy手机", "手机", "荣耀", 2700.00));
        list.add(new Item(6L, "zzz手机", "手机", "苹果", 3200.00));
        itemRepository.saveAll(list);
    }

    @Test
    public void testDel(){
        itemRepository.deleteById(3L);
    }

    @Test
    public void testQuery(){
        Optional<Item> optional = itemRepository.findById(2L);
        System.out.println(optional.get());
    }

    @Test
    public void testFind(){
        Iterable<Item> items = itemRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        items.forEach(item -> System.out.println(item));
    }

    @Test
    public void testQueryByPriceBetween(){
        List<Item> byPriceBetween = itemRepository.findByPriceBetween(2000.00, 3000.00);
        for (Item item : byPriceBetween) {
            System.out.println("item =  " + item);
        }
    }



    
}
