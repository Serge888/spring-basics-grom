package com.lesson5;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ItemController {
    private ItemDao itemDao;

    @Autowired
    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }


    @RequestMapping (method = RequestMethod.POST, value = "/save-item", produces = "text/plain")
    public @ResponseBody
    String saveItem(HttpServletRequest req) {
        try {
            itemDao.saveItem(mapItem(req));
        } catch (Exception e) {
            return "save - fail";
        }
        return "save - ok";
    }



    @RequestMapping (method = RequestMethod.POST, value = "/update-item", produces = "text/plain")
    public @ResponseBody
    String updateItem(HttpServletRequest req) {
        try {
//            Item item = itemDao.findById(mapItem(req).getId());
            itemDao.updateItem(mapItem(req).getId());
        } catch (Exception e) {
            return "update - fail";
        }
        return "update - ok";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/delete-item", produces = "text/plain")
    public @ResponseBody
    String deleteItem(HttpServletRequest req) {
        try {
            Item item = itemDao.findById(mapItem(req).getId());
            itemDao.deleteItem(item);
        } catch (Exception e) {
            return "delete - fail";
        }
        return "delete - ok";
    }


    private Item mapItem(HttpServletRequest req) {
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = new Item();
        try {
            item = objectMapper.readValue(req.getInputStream(), item.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
