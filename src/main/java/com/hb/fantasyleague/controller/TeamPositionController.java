package com.hb.fantasyleague.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hb.fantasyleague.aspect.LoggingMethod;
import com.hb.fantasyleague.dto.TeamPositionDto;
import com.hb.fantasyleague.model.TeamPostionRequest;
import com.hb.fantasyleague.service.TeamPositionService;


import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/service/v1/team/position")
@Slf4j
public class TeamPositionController {

	private TeamPositionService teamPositionService;

	@Autowired
	public TeamPositionController(TeamPositionService teamPositionService) {
		this.teamPositionService = teamPositionService;
	}	

	@LoggingMethod
	@GetMapping
	public ResponseEntity<TeamPositionDto> getStandings(@RequestBody TeamPostionRequest teamPostionRequest) {
		log.info("inside getStanding -->");
		return ResponseEntity.ok(teamPositionService.getTeamStanding(teamPostionRequest));
	}

}
