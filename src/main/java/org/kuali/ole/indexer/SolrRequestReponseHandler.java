package org.kuali.ole.indexer;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.kuali.ole.spring.ApplicationContextProvider;
import org.kuali.ole.spring.PropertyValueProvider;

import java.io.IOException;
import java.util.*;

/**
 * Created by sheiks on 27/10/16.
 */
public class SolrRequestReponseHandler{

    private static final Logger LOG = Logger.getLogger(SolrRequestReponseHandler.class);

    private HttpSolrServer server;

    PropertyValueProvider propertyValueProvider;

    public List retriveResults(String queryString) {
        ArrayList<HashMap<String, Object>> hitsOnPage = new ArrayList<>();

        server = getHttpSolrServer();

        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);
        query.setIncludeScore(true);

        try {
            QueryResponse qr = server.query(query);

            SolrDocumentList sdl = qr.getResults();


            for (SolrDocument d : sdl) {
                HashMap<String, Object> values = new HashMap<String, Object>();

                for (Iterator<Map.Entry<String, Object>> i = d.iterator(); i.hasNext(); ) {
                    Map.Entry<String, Object> e2 = i.next();

                    values.put(e2.getKey(), e2.getValue());
                }

                hitsOnPage.add(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hitsOnPage;
    }

    public List retrieveResultsForNotice(String queryString) {
        ArrayList<HashMap<String, Object>> hitsOnPage = new ArrayList<>();

        server = getHttpSolrServer();

        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);

        try {
            QueryResponse qr = server.query(query);
            int numFound = (int)qr.getResults().getNumFound();
            query.setRows(Integer.valueOf(numFound));
            qr = server.query(query);
            SolrDocumentList sdl = qr.getResults();


            for (SolrDocument d : sdl) {
                HashMap<String, Object> values = new HashMap<String, Object>();

                for (Iterator<Map.Entry<String, Object>> i = d.iterator(); i.hasNext(); ) {
                    Map.Entry<String, Object> e2 = i.next();

                    values.put(e2.getKey(), e2.getValue());
                }

                hitsOnPage.add(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hitsOnPage;
    }

    public SolrDocumentList getSolrDocumentList(String queryString) {
        ArrayList<HashMap<String, Object>> hitsOnPage = new ArrayList<>();
        SolrDocumentList sdl = null;

        server = getHttpSolrServer();

        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);
        query.setIncludeScore(true);

        try {
            QueryResponse qr = server.query(query);
            sdl = qr.getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdl;
    }

    public SolrDocumentList getSolrDocumentList(String queryString, Integer start, Integer rows, String fieldList) {
        ArrayList<HashMap<String, Object>> hitsOnPage = new ArrayList<>();
        SolrDocumentList sdl = null;

        server = getHttpSolrServer();

        SolrQuery query = new SolrQuery();
        query.setQuery(queryString);
        if(null != start) {
            query.setStart(start);
        }
        if(null != rows) {
            query.setRows(rows);
        }
        query.setFields(fieldList);
        query.setIncludeScore(true);

        try {
            QueryResponse qr = server.query(query);
            sdl = qr.getResults();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdl;
    }

    private HttpSolrServer getHttpSolrServer() {
        if (null == server) {
            try {
                String serverUrl = getSolrUrl();
                server = new HttpSolrServer(serverUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return server;
    }

    public String getSolrUrl() {
        String solrURL = getPropertyValueProvider().getProperty("solr.url");
        LOG.info("Solr URl : " + solrURL);
        return solrURL;
    }

    public UpdateResponse updateSolr(List<SolrInputDocument> solrInputDocument) {
        UpdateResponse updateResponse = null;
        try {
            updateResponse = getHttpSolrServer().add(solrInputDocument);
            updateResponse = server.commit();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error while updating document to solr.");
        }
        return updateResponse;
    }

    public UpdateResponse deleteFromSolr(String query){
        UpdateResponse updateResponse = null;
        try {
            updateResponse = getHttpSolrServer().deleteByQuery(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse;
    }

    public UpdateResponse updateSolr(List<SolrInputDocument> solrInputDocument, boolean isCommit) {
        UpdateResponse updateResponse = null;
        try {
            updateResponse = getHttpSolrServer().add(solrInputDocument);
            if (isCommit) {
                updateResponse = getHttpSolrServer().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error while updating document to solr.");
        }
        return updateResponse;
    }

    public UpdateResponse commitToServer() {
        UpdateResponse updateResponse = null;
        try {
            updateResponse = getHttpSolrServer().commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse;
    }

    public PropertyValueProvider getPropertyValueProvider() {
        if(null == propertyValueProvider) {
            ApplicationContextProvider instance = ApplicationContextProvider.getInstance();
            propertyValueProvider = instance.getApplicationContext().getBean(PropertyValueProvider.class);
        }
        return propertyValueProvider;
    }

    public void setPropertyValueProvider(PropertyValueProvider propertyValueProvider) {
        this.propertyValueProvider = propertyValueProvider;
    }
}
