package org.kuali.ole.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.ole.executor.BibIndexExecutorService;
import org.kuali.ole.request.FullIndexRequest;
import org.kuali.ole.response.FullIndexStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

/**
 * Created by sheiks on 27/10/16.
 */
@RestController
@EnableAsync
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    BibIndexExecutorService bibIndexExecutorService;

    @Value("${numberOfDbThread}")
    private String noOfDbThread;

    @Value("${docPerThread}")
    private String docPerThread;

    @Value("${noOfBibProcessThreads}")
    private String noOfBibProcessThreads;

    private FullIndexStatus fullIndexStatus = FullIndexStatus.getInstance();


    @RequestMapping("/")
    public ModelAndView main(Model model) {
        return new ModelAndView("index");
    }

    @RequestMapping("/index")
    public ModelAndView index(Model model) {
        return new ModelAndView("index");
    }

    @RequestMapping("/authenticate")
    public Principal authenticate(Principal user) {
        return user;
    }

    @ResponseBody
    @RequestMapping(value="/fullIndex", method = RequestMethod.POST, produces = "application/json")
    public FullIndexStatus fullIndex(@RequestBody FullIndexRequest fullIndexRequest) {
        fullIndexRequest = initiateFullIndexRequest(fullIndexRequest);
        this.fullIndexStatus.resetStatus();
        bibIndexExecutorService.indexDocument(fullIndexRequest);
        return this.fullIndexStatus;
    }

    private FullIndexRequest initiateFullIndexRequest(FullIndexRequest fullIndexRequest) {
        if(null == fullIndexRequest) {
            fullIndexRequest = new FullIndexRequest();
        }

        if(null == fullIndexRequest.getDocsPerThread() || fullIndexRequest.getDocsPerThread() <= 0) {
            fullIndexRequest.setDocsPerThread(Integer.valueOf(docPerThread));
        }

        if(null == fullIndexRequest.getNoOfDbThreads() || fullIndexRequest.getNoOfDbThreads() <= 0) {
            fullIndexRequest.setNoOfDbThreads(Integer.valueOf(noOfDbThread));
        }

        if(null == fullIndexRequest.getNoOfBibProcessThreads() || fullIndexRequest.getNoOfBibProcessThreads() <= 0) {
            fullIndexRequest.setNoOfBibProcessThreads(Integer.valueOf(noOfBibProcessThreads));
        }
        return fullIndexRequest;

    }

    @ResponseBody
    @RequestMapping(value="/fullIndexStatus", method = RequestMethod.GET, produces = "application/json")
    public FullIndexStatus fullIndexStatus() {
        return this.fullIndexStatus;
    }

    private void addObjectToJsonObject(JSONObject response, String key, String value) {
        try {
            response.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
