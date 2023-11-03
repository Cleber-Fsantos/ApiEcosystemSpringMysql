package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.CategoriaDto;
import com.senai.apiecosystem.models.CategoriaModel;
import com.senai.apiecosystem.repositories.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categoria", produces = {"application/json"})
public class CategoriaController {
    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<CategoriaModel>> listarCategorias(){
        return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarCategoria(@RequestBody @Valid CategoriaDto categoriaDto) {
        CategoriaModel CategoriaNew = new CategoriaModel();
        BeanUtils.copyProperties(categoriaDto, CategoriaNew);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(CategoriaNew));
    }

}
