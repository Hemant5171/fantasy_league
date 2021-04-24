package com.hb.fantasyleague.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.fantasyleague.client.FootballApiClient;
import com.hb.fantasyleague.dto.TeamPositionDto;
import com.hb.fantasyleague.model.TeamPosition;
import com.hb.fantasyleague.model.TeamPostionRequest;
import com.hb.fantasyleague.model.Country;
import com.hb.fantasyleague.model.Leagues;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamPositionService {

	private final FootballApiClient footballApiClient;

	@Autowired
	public TeamPositionService(FootballApiClient footballApiClient) {
		this.footballApiClient = footballApiClient;
	}

	public TeamPositionDto getTeamStanding(TeamPostionRequest teamPostionRequest) {
		// Validate input
		TeamPosition teamStanding = initializePosition(teamPostionRequest);
		List<Country> countries = getCountries();
		Country country = getCountryByName(teamPostionRequest, countries);
		if(null!=country)
		{
		teamStanding.setCountryId(country.getId());
		}
		else {
			return null;
		}

		List<Leagues> leaguesList = getLeagues(country.getId());
		Leagues leagues = getLeaguesByName(teamPostionRequest, leaguesList);
		if(null!=leagues)
		{
		teamStanding.setLeagueId(leagues.getLeagueId());
		}
		else {
			return null;
		}
		List<TeamPosition> teamStandings = getTeamStanding(leagues.getLeagueId());
		log.info("team standing found {}", teamStandings.toString());

		TeamPosition teamStandingsFiltered = getFilteredTeamStanding(teamPostionRequest, teamStandings);
		if(null!=teamStandingsFiltered)
		{
		teamStandingsFiltered.setCountryId(country.getId());
		}
		else {
			return null;
		}
		log.info("team standing filtered found {}", teamStandingsFiltered.toString());
		if (teamStandingsFiltered.getTeamId() == 0) {
			return TeamPositionDto.populateTeamPositionDto(teamStanding);
		}
		return TeamPositionDto.populateTeamPositionDto(teamStanding);
	}

	private TeamPosition initializePosition(TeamPostionRequest teamPostionRequest) {
		TeamPosition teamPosition = new TeamPosition();
		teamPosition.setTeamName(teamPostionRequest.getTeamName());
		teamPosition.setCountryName(teamPostionRequest.getCountryName());
		teamPosition.setLeagueName(teamPostionRequest.getLeagueName());
		return teamPosition;
	}
	
	 private Country getCountryByName(TeamPostionRequest teamPostionRequest,
		      List<Country> countries) {
		    return countries.stream()
		        .filter(c -> teamPostionRequest.getCountryName().equalsIgnoreCase(c.getName())).findFirst()
		        .orElse(null);
		  }

		  private Leagues getLeaguesByName(TeamPostionRequest teamPostionRequest,
		      List<Leagues> leaguesList) {
		    return leaguesList.stream()
		        .filter(l -> teamPostionRequest.getLeagueName().equalsIgnoreCase(l.getLeagueName()))
		        .findFirst().orElse(null);
		  }

		  private TeamPosition getFilteredTeamStanding(TeamPostionRequest teamPostionRequest,
		      List<TeamPosition> teamPositions) {
		    return teamPositions.stream()
		        .filter(t -> teamPostionRequest.getTeamName().equalsIgnoreCase(t.getTeamName()))
		        .findFirst().orElse(null);
		  }
	
	private List<Country> getCountries() {
	    return new ArrayList<>(Arrays.asList(footballApiClient.getCountries()));
	  }

	  private List<Leagues> getLeagues(int countryId) {
	    return new ArrayList<>(Arrays.asList(footballApiClient.getLeagues(countryId)));
	  }


	  private List<TeamPosition> getTeamStanding(int leagueId) {
	    return new ArrayList<>(Arrays.asList(footballApiClient.getTeamStanding(leagueId)));
	  }

}
