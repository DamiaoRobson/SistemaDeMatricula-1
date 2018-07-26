package com.SistemaDeMatricula.SistemaDeMatricula.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.SistemaDeMatricula.SistemaDeMatricula.Service.CoordenadorService;
import com.SistemaDeMatricula.SistemaDeMatricula.models.Coordenador;

public class CoordenadorController {
	
	@Autowired
	CoordenadorService coordenadorService;

	@RequestMapping(value = "/api/coord/post", method = RequestMethod.POST)
	public ResponseEntity<Coordenador> add(@RequestBody Coordenador coordenador) {
		return new ResponseEntity<>(coordenadorService.add(coordenador),HttpStatus.OK);
	}
	
	@RequestMapping(value = "api/coord/get", method = RequestMethod.GET)
	public ResponseEntity<Collection<Coordenador>> busca() {
		return new ResponseEntity<>(coordenadorService.buscarTodos(), HttpStatus.OK);
	}
}
