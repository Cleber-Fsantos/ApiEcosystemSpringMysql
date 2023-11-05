package com.senai.apiecosystem.controllers;


import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.dtos.ProdutoDto;
import com.senai.apiecosystem.models.EnderecoModel;
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
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{idProduto}")
    public ResponseEntity<Object> exibirProduto(@PathVariable(value = "idProduto") UUID id) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produtp não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoBuscado.get());
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

    @PutMapping(value = "/{idProduto}")
    public ResponseEntity<Object> editarProduto(@PathVariable(value = "idProduto") UUID id, @RequestBody @Valid ProdutoDto produtoDto) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        ProdutoModel produto = produtoBuscado.get();
        BeanUtils.copyProperties(produtoDto, produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Object> deletarProduto(@PathVariable(value = "idProduto") UUID id) {
        Optional<ProdutoModel> produtoBuscado = produtoRepository.findById(id);

        if (produtoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }

        produtoRepository.delete(produtoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto deletado com sucesso!");
    }

}
