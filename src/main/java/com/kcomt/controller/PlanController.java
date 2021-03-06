package com.kcomt.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kcomt.entities.Plans;
import com.kcomt.service.IPlansService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/plans")
@Api(value="REST review")
public class PlanController {

	@Autowired
	private IPlansService plansService;
	
	@ApiOperation("Registro de planes")
	@PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> saveSale(@Valid @RequestBody Plans plans)
	{
		try {
			Plans hor = new Plans();
			hor= plansService.save(plans);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(hor.getId()).toUri();
			
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
					
		}
	}
	
	@ApiOperation("Obtener balance por dni de cliente")
	@GetMapping(value = "/client/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Plans> fetchbuisnessOwner(@PathVariable("dni") String dni) {

		try {
			Plans bO = plansService.findPlansbyClientDNI(dni);
			return new ResponseEntity<Plans>(bO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Plans>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
