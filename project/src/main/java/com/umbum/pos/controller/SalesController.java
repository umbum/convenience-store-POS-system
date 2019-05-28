package com.umbum.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @RequestMapping(method= RequestMethod.GET)
    public String sales() {
        return "sales.html";
    }

    @RequestMapping("/datatable")
    public String salesDataTable() {
        return "sales_datatable.html";
    }

}
