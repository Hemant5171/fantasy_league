 package com.hb.fantasyleague.fantasyleague;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.fantasyleague.client.FootballApiClient;
import com.hb.fantasyleague.config.FantasyLeagueConfig;
import com.hb.fantasyleague.controller.TeamPositionController;
import com.hb.fantasyleague.model.Country;
import com.hb.fantasyleague.model.Leagues;
import com.hb.fantasyleague.model.TeamPosition;
import com.hb.fantasyleague.model.TeamPostionRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamPositionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FootballApiClient footballApiClient;

	@Test
	public void getStanding() throws Exception {
		
		Country[] countries= {getDummyCountry()};
		Leagues[] leagues= {getDummyLeagues()};
		TeamPosition[] teamPosition= {getTeamPosition()};
		
		
		when(footballApiClient.getCountries()).thenReturn(countries);
		when(footballApiClient.getLeagues(100)).thenReturn(leagues);
		when(footballApiClient.getTeamStanding(1000)).thenReturn(teamPosition);
		
		this.mockMvc.perform(get("/api/service/v1/team/position").contentType(MediaType.APPLICATION_JSON_UTF8).content(asJsonString(getTestTeamPositionRequest())))
		.andDo(print()).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.country").value("(100) - India"))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.team").value("(0) - Rajasthan Royals"))
	    .andExpect(MockMvcResultMatchers.jsonPath("$.positions").value("0"));
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
		
	
	public TeamPostionRequest getTestTeamPositionRequest()
	{
		TeamPostionRequest teamPostionRequest=new TeamPostionRequest();
		teamPostionRequest.setCountryName("India");
		teamPostionRequest.setLeagueName("IPL");
		teamPostionRequest.setTeamName("Rajasthan Royals");
		return teamPostionRequest;
	}
	
	public Country getDummyCountry()
	{
		Country country=new Country();
		country.setId(100);
		country.setName("India");
		return country;
	}
	
	public Leagues getDummyLeagues()
	{
		Leagues leagues=new Leagues();
		leagues.setCountryId(100);
		leagues.setCountryName("India");
		leagues.setLeagueId(1000);
		leagues.setLeagueName("IPL");
		return leagues;
	}
	
	public TeamPosition getTeamPosition()
	{
		TeamPosition teamPosition=new TeamPosition();
		teamPosition.setCountryId(100);
		teamPosition.setCountryName("India");
		teamPosition.setLeagueId(1000);
		teamPosition.setLeagueName("IPL");
		teamPosition.setOverallPosition(1);
		teamPosition.setTeamId(1);
		teamPosition.setTeamName("Rajasthan Royals");
		return teamPosition;
	}
	
}
