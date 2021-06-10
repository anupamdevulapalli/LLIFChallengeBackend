package com.llifinterview.challenge.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResponseMessageTest {

    @Test
    public void testResponseMessage () {
        final ResponseMessage rm = new ResponseMessage( "Hello" );
        assertEquals( rm.getMessage(), "Hello" );

        rm.setMessage( "Bye" );
        assertEquals( rm.getMessage(), "Bye" );
    }

}
