package com.example.L09_Logging_MVC_Annotation_demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    private static Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/dynamic-list")
    public ModelAndView getList() {
        LOGGER.info("Processing dynamic-list");
        List<Product> productList = productService.getAll();
        ModelAndView modelAndView = new ModelAndView("dynamic-page.html");
        modelAndView.getModelMap().put("products",productList);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        modelAndView.getModelMap().put("serverTime",dateFormat.format(new Date()));
        return modelAndView;
    }

}
