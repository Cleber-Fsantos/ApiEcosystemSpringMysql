package com.senai.apiecosystem.controllers;


import com.senai.apiecosystem.dtos.UsuarioDto;
import com.senai.apiecosystem.models.UsuarioModel;
import com.senai.apiecosystem.repositories.TipoUsuarioRepository;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios", produces = {"application/json"})
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        if (usuarioRepository.findByEmail(usuarioDto.email()) != null) {
            // Não pode cadastrar
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        UsuarioModel usuarioModel = new UsuarioModel();

        BeanUtils.copyProperties(usuarioDto, usuarioModel);

        var tipoUsuario = tipoUsuarioRepository.findById(usuarioDto.id_tipousuario());

        if (tipoUsuario.isPresent()) {
            usuarioModel.setTipousuario(tipoUsuario.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_tipousuario não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
    }



}


