package com.hb.fantasyleague.fantasyleague;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hb.fantasyleague.client.FootballApiClient;
import com.hb.fantasyleague.model.Country;

//@SpringBootTest
public class FootballApiClientTest {

	@Autowired
	private FootballApiClient footballApiClient;

   // @Test
    public void testGetAllCountries() {
    	Country[] cuntCountries= footballApiClient.getCountries();
    	
    }
    
}
