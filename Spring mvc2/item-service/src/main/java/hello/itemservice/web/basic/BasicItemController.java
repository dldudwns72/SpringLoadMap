package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 것들을 생성자로 만들어 준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

//    public BasicItemController(ItemRepository itemRepository){
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }


    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String save(@ModelAttribute("item") Item item,
//                       @RequestParam String itemName,
//                       @RequestParam int price,
//                       @RequestParam Integer quantity,
                       Model model){

//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setQuantity(quantity);
//        item.setPrice(price);

        itemRepository.save(item);

//        model.addAttribute("item",item);
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV2(Item item, Model model){
        itemRepository.save(item);
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemRedirect(Item item, Model model){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemRedirectAttribute(Item item, RedirectAttributes redirectAttributes){
        itemRepository.save(item);

        // Query Parameter로 변환되어서 입력된다.
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect://basic/items/" + item.getId();
    }



    @GetMapping("{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }


    // 테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }



}
