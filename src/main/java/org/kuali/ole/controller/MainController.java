package org.kuali.ole.controller;

import org.kuali.ole.executor.BibIndexExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sheiks on 27/10/16.
 */
@RestController
public class MainController {

    @Autowired
    BibIndexExecutorService bibIndexExecutorService;


    @RequestMapping("/")
    public ModelAndView main(Model model) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                bibIndexExecutorService.indexDocument();
            }
        });
        return new ModelAndView("index");
    }

    @RequestMapping("/index")
    public ModelAndView index(Model model) {
        return new ModelAndView("index");
    }
}
