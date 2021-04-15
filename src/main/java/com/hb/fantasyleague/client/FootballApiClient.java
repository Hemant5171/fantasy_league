package com.hb.fantasyleague.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hb.fantasyleague.model.Country;
import com.hb.fantasyleague.model.Leagues;
import com.hb.fantasyleague.model.TeamPosition;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class FootballApiClient {

	  private static final String API = "APIkey";
	  private final RestTemplate restTemplate;

	  @Value("${football.api.base.url}")
	  private String baseUrl;

	  @Value("${football.api.action.standings}")
	  private String standingsAction;

	  @Value("${football.api.action.countries}")
	  private String countriesAction;

	  @Value("${football.api.action.leagues}")
	  private String leaguesAction;

	  @Value("${football.api.key}")
	  private String api;
	  
	  @Autowired
	  public FootballApiClient(RestTemplate restTemplate)
	  {
		  this.restTemplate=restTemplate;
	  }
	  
	  @HystrixCommand(fallbackMethod = "getCountries_default",  commandProperties = {
	  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	  public Country[] getCountries() {
		    Map<String, String> queryParams = new HashMap<>();
		    queryParams.put("action", countriesAction);
		    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		    return this.restTemplate
		        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
		            Country[].class).getBody();
		  }
	  
	  private Country[] getCountries_default() {
		    return new Country[]{new Country()};
		  }

	  
	  @HystrixCommand(fallbackMethod = "getLeagues_default",  commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	  public Leagues[] getLeagues(int countryId) {
		    Map<String, String> queryParams = new HashMap<>();
		    queryParams.put("action", leaguesAction);
		    queryParams.put("country_id", String.valueOf(countryId));
		    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		    return this.restTemplate
		        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
		            Leagues[].class).getBody();
		  }
	  
	  private Leagues[] getLeagues_default(int countryId) {
		    Leagues leagues = new Leagues();
		    leagues.setCountryId(countryId);
		    return new Leagues[]{leagues};
		  }

	  
	  @HystrixCommand(fallbackMethod = "getTeamStanding_default",  commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000") })
	  public TeamPosition[] getTeamStanding(int leagueId) {
		    Map<String, String> queryParams = new HashMap<>();
		    queryParams.put("action", standingsAction);
		    queryParams.put("league_id", String.valueOf(leagueId));
		    UriComponentsBuilder builder = getUriComponentsBuilder(baseUrl, queryParams);
		    return this.restTemplate
		        .exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<>(getHeaders()),
		            TeamPosition[].class).getBody();
		  }
	  
	  private TeamPosition[] getTeamStanding_default(int leagueId) {
		  TeamPosition teamPosition = new TeamPosition();
		  teamPosition.setLeagueId(leagueId);
		    return new TeamPosition[]{teamPosition};
		  }
	  
	  private UriComponentsBuilder getUriComponentsBuilder(String url,
		      Map<String, String> queryParams) {
		    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam(API, api);
		    queryParams.forEach(builder::queryParam);
		    return builder;
		  }

		  private HttpHeaders getHeaders() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		    return headers;
		  }
	
}
