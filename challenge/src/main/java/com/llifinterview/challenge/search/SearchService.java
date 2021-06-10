package com.llifinterview.challenge.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

/**
 * Search service
 * 
 * @author Anupam Devulapalli
 *
 */
@Service
public class SearchService {
    private final Path        index    = Paths.get( "indexedFiles" );
    private Directory         direc    = null;
    private IndexWriterConfig iwc      = null;
    private IndexWriter       writer   = null;
    private IndexSearcher     searcher = null;

    /**
     * Initializes the folder where all the indexed files will be stored
     */
    public void init () {
        try {
            if ( !Files.exists( index ) ) {
                Files.createDirectory( index );
            }

        }
        catch ( final IOException e ) {
            System.out.println( e.getMessage() );
            throw new RuntimeException( "Error creating indexed files folder. Error: " + e.getMessage() );
        }
    }

    public Analyzer getAnalyzer () {
        return new StandardAnalyzer();
    }

    public IndexSearcher getSearcher () {
        return this.searcher;
    }

    /**
     * Adding the file to index
     *
     * @param file
     * @throws IOException
     */
    public void addToIndex ( Path file ) throws IOException {
        direc = FSDirectory.open( index );
        this.iwc = new IndexWriterConfig( getAnalyzer() );
        iwc.setOpenMode( OpenMode.CREATE_OR_APPEND );
        this.writer = new IndexWriter( this.direc, this.iwc );
        final Document doc = new Document();

        doc.add( new StringField( "path", file.toString(), Field.Store.YES ) );
        doc.add( new TextField( "contents", new String( Files.readAllBytes( file ) ), Field.Store.YES ) );
        writer.updateDocument( new Term( "path", file.toString() ), doc );
        writer.close();
    }

    /**
     * Using basic lucene to search for specific query
     *
     * @param query
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public TopDocs searchWithQuery ( String query ) throws ParseException, IOException {
        // For searching the indexed files
        searcher = new IndexSearcher( DirectoryReader.open( FSDirectory.open( index ) ) );
        final QueryParser qp = new QueryParser( "contents", getAnalyzer() );
        final Query q = qp.parse( query );
        return this.searcher.search( q, 10 );
    }
}
