package com.developersam.web.model.chunkreader.knowledge

import com.developersam.web.model.chunkreader.ChunkReaderProcessor
import com.developersam.web.model.chunkreader.NLPAPIAnalyzer
import com.google.appengine.api.datastore.Key

/**
 * The class used to help build a knowledge graph.
 * It will extract useful information from the API and store them into the
 * database.
 */
class KnowledgeGraphBuilder : ChunkReaderProcessor {
    override fun process(analyzer: NLPAPIAnalyzer, textKey: Key) {
        TODO("not implemented")
    }
}