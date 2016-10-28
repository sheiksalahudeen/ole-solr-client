package org.kuali.ole.model.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

/**
 * Created by sheiks on 27/10/16.
 */
public class Bib {
    @Id
    @Field("id")
    private String id;
}
