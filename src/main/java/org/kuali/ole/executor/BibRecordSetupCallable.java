package org.kuali.ole.executor;

import org.apache.camel.ProducerTemplate;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.indexer.BibIndexer;
import org.kuali.ole.indexer.ConfigMaps;
import org.kuali.ole.model.jpa.BibRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by sheiks on 28/10/16.
 */
public class BibRecordSetupCallable implements Callable{
    private BibRecord bibRecord;
    private BibMarcRecordProcessor bibMarcRecordProcessor;
    private Map<String, String> fieldsToTags2IncludeMap = new HashMap<>();
    private Map<String, String> fieldsToTags2ExcludeMap = new HashMap<>();
    private ProducerTemplate producerTemplate;

    public BibRecordSetupCallable(BibRecord bibRecord, BibMarcRecordProcessor bibMarcRecordProcessor,
                                  Map<String, String> fieldsToTags2IncludeMap,
                                  Map<String, String> fieldsToTags2ExcludeMap,
                                  ProducerTemplate producerTemplate) {
        this.bibRecord = bibRecord;
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
        this.fieldsToTags2IncludeMap.putAll(fieldsToTags2IncludeMap);
        this.fieldsToTags2ExcludeMap.putAll(fieldsToTags2ExcludeMap);
        this.producerTemplate = producerTemplate;
    }

    @Override
    public Object call() throws Exception {
        BibIndexer bibIndexer = new BibIndexer();
        bibIndexer.setBibMarcRecordProcessor(bibMarcRecordProcessor);
        ConfigMaps configMaps = new ConfigMaps();
        configMaps.setFIELDS_TO_TAGS_2_INCLUDE_MAP(fieldsToTags2IncludeMap);
        configMaps.setFIELDS_TO_TAGS_2_EXCLUDE_MAP(fieldsToTags2ExcludeMap);
        bibIndexer.setConfigMaps(configMaps);
        bibIndexer.setProducerTemplate(producerTemplate);
        return bibIndexer.prepareSolrInputDocument(bibRecord);
    }

}
