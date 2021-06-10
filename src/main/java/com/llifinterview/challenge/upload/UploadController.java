package com.llifinterview.challenge.upload;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.llifinterview.challenge.search.SearchService;

@Controller
@CrossOrigin ( "http://localhost:4200" ) // 4200 is the port for the angular
                                         // container
public class UploadController {
    @Autowired
    UploadService uploadService;

    @Autowired
    SearchService searchService;

    @PostMapping ( "/upload" )
    public ResponseEntity<ResponseMessage> uploadFile ( @RequestParam ( "file" ) MultipartFile file ) {
        String message = "";
        try {

            // Saving a local copy
            final Path uploadedPath = uploadService.save( file );
            // Adding it to the index
            searchService.addToIndex( uploadedPath );
            message = "Uploaded and Indexed the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status( HttpStatus.OK ).body( new ResponseMessage( message ) );
        }
        catch ( final Exception e ) {
            message = "Could not upload the file: " + file.getOriginalFilename();
            System.out.println( e.getMessage() );
            return ResponseEntity.status( HttpStatus.EXPECTATION_FAILED ).body( new ResponseMessage( message ) );
        }
    }

    /**
     * Unsuccessful implementation for accepting multiple files together
     */

    // @PostMapping ( "/uploads" )
    // public ResponseEntity<ResponseMessage> uploadFiles ( @RequestParam (
    // "files" ) MultipartFile[] files ) {
    // String message = "";
    // try {
    // Path uploadedPath = null;
    // for ( final MultipartFile file : files ) {
    // uploadedPath = uploadService.save( file );
    // searchService.addToIndex( uploadedPath );
    // }
    //
    // message = "Uploaded and Indexed " + files.length + " file(s)
    // successfully";
    //
    // return ResponseEntity.status( HttpStatus.OK ).body( new ResponseMessage(
    // message ) );
    // }
    // catch ( final Exception e ) {
    // message = "Could not upload the files";
    // System.out.println( "AAA " + e.getMessage() );
    // return ResponseEntity.status( HttpStatus.EXPECTATION_FAILED ).body( new
    // ResponseMessage( message ) );
    // }
    // }
}
