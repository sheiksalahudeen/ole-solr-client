package org.kuali.ole.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by sheiks on 27/10/16.
 */
@RestController
public class MainController {


    @RequestMapping("/")
    public ModelAndView main(Model model) {
        return new ModelAndView("index");
    }

    @RequestMapping("/index")
    public ModelAndView index(Model model) {
        return new ModelAndView("index");
    }
}
