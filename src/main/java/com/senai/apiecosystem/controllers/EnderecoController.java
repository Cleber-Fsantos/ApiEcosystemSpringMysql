package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.models.EnderecoModel;
import com.senai.apiecosystem.repositories.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/endereco", produces = {"application/json"})
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listarEnderecos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarEndereco(@RequestBody @Valid EnderecoDto enderecoDto) {
        EnderecoModel Endereco = new EnderecoModel();
        BeanUtils.copyProperties(enderecoDto, Endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(Endereco));
    }

}
