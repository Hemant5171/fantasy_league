package com.hb.fantasyleague.dto;

import com.hb.fantasyleague.model.TeamPosition;

import lombok.Data;

@Data
public class TeamPositionDto {

	private String country;
	private String league;
	private String team;
	private int positions;

	public static TeamPositionDto populateTeamPositionDto(TeamPosition teamPosition) {
		TeamPositionDto dto = new TeamPositionDto();
		if (null!=teamPosition) {
			dto.setCountry("(" + teamPosition.getCountryId() + ") - " + teamPosition.getCountryName());
			dto.setLeague("(" + teamPosition.getLeagueId() + ") - " + teamPosition.getLeagueName());
			dto.setTeam("(" + teamPosition.getTeamId() + ") - " + teamPosition.getTeamName());
			dto.setPositions(teamPosition.getOverallPosition());
		}
		return dto;

	}

}
