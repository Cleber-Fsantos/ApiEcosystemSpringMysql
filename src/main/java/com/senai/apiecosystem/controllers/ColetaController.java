package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.ColetaDto;
import com.senai.apiecosystem.models.ColetaModel;
import com.senai.apiecosystem.repositories.AnuncioRepository;
import com.senai.apiecosystem.repositories.ColetaRepository;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/coleta", produces = {"application/json"})
public class ColetaController {
    @Autowired
    ColetaRepository coletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @GetMapping
    public ResponseEntity<List<ColetaModel>> listarColetas(){
        return ResponseEntity.status(HttpStatus.OK).body(coletaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarColeta(@RequestBody @Valid ColetaDto coletaDto) {
        ColetaModel coletaModel = new ColetaModel();

        BeanUtils.copyProperties(coletaDto, coletaModel);

        var usuario = usuarioRepository.findById(coletaDto.id_usuario());
        var anuncio = anuncioRepository.findById(coletaDto.id_anuncio());
        LocalDate date = LocalDate.now();

        if (usuario.isPresent() && anuncio.isPresent()) {
            coletaModel.setUsuario(usuario.get());
            coletaModel.setAnuncio(anuncio.get());
            coletaModel.setData_cadastro(date);

        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_usuario ou id_anuncio não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coletaModel));
    }

}


