package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.AnuncioDto;
import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.repositories.AnuncioRepository;
import com.senai.apiecosystem.repositories.ProdutoRepository;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/anuncio", produces = {"application/json"})
public class AnuncioController {
    @Autowired
    AnuncioRepository anuncioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<AnuncioModel>> listarAnuncios(){
        return ResponseEntity.status(HttpStatus.OK).body(anuncioRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<Object> cadastrarAnuncio(@RequestBody @Valid AnuncioDto anuncioDto) {
        AnuncioModel anuncioModel = new AnuncioModel();

        BeanUtils.copyProperties(anuncioDto, anuncioModel);

        var usuario = usuarioRepository.findById(anuncioDto.id_usuario());
        var produto = produtoRepository.findById(anuncioDto.id_produto());
        LocalDate date = LocalDate.now();

        if (usuario.isPresent() && produto.isPresent()) {
            anuncioModel.setUsuario(usuario.get());
            anuncioModel.setProduto(produto.get());
            anuncioModel.setData_cadastro(date);

        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_usuario ou id_produto não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(anuncioRepository.save(anuncioModel));
    }



}


