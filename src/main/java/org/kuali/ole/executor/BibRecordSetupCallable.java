package org.kuali.ole.executor;

import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.indexer.BibIndexer;
import org.kuali.ole.model.jpa.BibRecord;

import java.util.concurrent.Callable;

/**
 * Created by sheiks on 28/10/16.
 */
public class BibRecordSetupCallable implements Callable{
    private BibRecord bibRecord;
    private BibMarcRecordProcessor bibMarcRecordProcessor;

    public BibRecordSetupCallable(BibRecord bibRecord, BibMarcRecordProcessor bibMarcRecordProcessor) {
        this.bibRecord = bibRecord;
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
    }

    @Override
    public Object call() throws Exception {
        BibIndexer bibIndexer = new BibIndexer();
        bibIndexer.setBibMarcRecordProcessor(bibMarcRecordProcessor);
        return bibIndexer.prepareSolrInputDocument(bibRecord);
    }
}
