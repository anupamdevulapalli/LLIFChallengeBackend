package com.llifinterview.challenge.upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
    private final Path root = Paths.get( "uploads" );

    public void init () {
        try {
            if ( !Files.exists( root ) ) {
                Files.createDirectory( root );
            }
        }
        catch ( final IOException e ) {
            System.out.println( e.getMessage() );
            throw new RuntimeException( "Error creating target folder. Error: " + e.getMessage() );
        }

    }

    public Path save ( MultipartFile file ) {
        try {
            Files.copy( file.getInputStream(), this.root.resolve( file.getOriginalFilename() ) );
            return this.root.resolve( file.getOriginalFilename() );
        }
        catch ( final Exception e ) {
            System.out.println( "BBB  " + e.getMessage() );
            throw new RuntimeException( "Did not save the file. Error: " + e.getMessage() );
        }
    }

}
