package com.llifinterview.challenge.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Serves as the end point for lucene search
 *
 * @author Anupam Devulapalli
 *
 */

@Controller
@CrossOrigin ( "http://localhost:4200" ) // 4200 is the port for the angular
                                         // container
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping ( "/search" )
    public ResponseEntity<List<String>> searchFiles ( @RequestParam ( "query" ) String query ) {
        final List<String> foundfiles = new ArrayList<>();
        if ( query == null || query == "" ) {
            return new ResponseEntity<List<String>>( foundfiles, HttpStatus.OK );
        }

        try {
            final TopDocs found = searchService.searchWithQuery( query );
            for ( final ScoreDoc sd : found.scoreDocs ) {
                final Document d = searchService.getSearcher().doc( sd.doc );
                foundfiles.add( d.get( "path" ).substring( 8 ) );
            }
            return new ResponseEntity<List<String>>( foundfiles, HttpStatus.OK );
        }
        catch ( ParseException | IOException e ) {
            System.out.println( e.getMessage() );
            // Returning empty list with failed status in case of error
            return new ResponseEntity<List<String>>( new ArrayList<>(), HttpStatus.EXPECTATION_FAILED );
        }
    }

}
