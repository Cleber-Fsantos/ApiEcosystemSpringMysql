package com.senai.apiecosystem.controllers;


import com.senai.apiecosystem.dtos.ProdutoDto;
import com.senai.apiecosystem.models.ProdutoModel;
import com.senai.apiecosystem.repositories.CategoriaRepository;
import com.senai.apiecosystem.repositories.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produto", produces = {"application/json"})
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listarProdutos(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarProduto(@RequestBody @Valid ProdutoDto produtoDto) {
        ProdutoModel produtoModel = new ProdutoModel();

        BeanUtils.copyProperties(produtoDto, produtoModel);

        var categoria = categoriaRepository.findById(produtoDto.id_categoria());

        if (categoria.isPresent()) {
            produtoModel.setCategoria(categoria.get());
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_categoria não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
    }

}
