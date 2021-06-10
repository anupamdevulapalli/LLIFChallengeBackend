package com.llifinterview.challenge.search;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith ( SpringRunner.class )
@SpringBootTest
public class SearchControllerTest {

    private MockMvc       mockMvc;
    @Autowired
    WebApplicationContext wContext;

    @Test
    public void testSearchFiles () throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( wContext ).alwaysDo( MockMvcResultHandlers.print() ).build();
        final String text = "Text to be uploaded.";
        final MockMultipartFile file = new MockMultipartFile( "file", "test.txt", "text/plain", text.getBytes() );
        mockMvc.perform( MockMvcRequestBuilders.multipart( "/upload" ).file( file ).characterEncoding( "UTF-8" ) );

        final LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add( "query", "Text" );

        mockMvc.perform( get( "/search" ).params( requestParams ) ).andExpect( status().isOk() );
    }

}
