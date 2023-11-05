package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.ColetaDto;
import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.models.ColetaModel;
import com.senai.apiecosystem.models.EnderecoModel;
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
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{idColeta}")
    public ResponseEntity<Object> exibirColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        return ResponseEntity.status(HttpStatus.OK).body(coletaBuscada.get());
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
        System.out.printf("  data de cadastro: %s ", coletaModel.getData_retirada());

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coletaModel));
    }

    @PutMapping(value = "/{idColeta}")
    public ResponseEntity<Object> editarColeta(@PathVariable(value = "idColeta") UUID id, @RequestBody @Valid ColetaDto coletaDto) {
        Optional<ColetaModel> coletaBuscada = coletaRepository.findById(id);

        if (coletaBuscada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        ColetaModel coleta = coletaBuscada.get();
        BeanUtils.copyProperties(coletaDto, coleta);

        return ResponseEntity.status(HttpStatus.CREATED).body(coletaRepository.save(coleta));
    }

    @DeleteMapping("/{idColeta}")
    public ResponseEntity<Object> deletarColeta(@PathVariable(value = "idColeta") UUID id) {
        Optional<ColetaModel> coletaBuscado = coletaRepository.findById(id);

        if (coletaBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coleta não encontrada");
        }

        coletaRepository.delete(coletaBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Coleta deletada com sucesso!");
    }

}


