package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.EnderecoDto;
import com.senai.apiecosystem.dtos.UsuarioDto;
import com.senai.apiecosystem.models.EnderecoModel;
import com.senai.apiecosystem.models.UsuarioModel;
import com.senai.apiecosystem.repositories.EnderecoRepository;
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
@RequestMapping(value = "/endereco", produces = {"application/json"})
public class EnderecoController {
    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<List<EnderecoModel>> listarEnderecos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll());
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Object> exibirEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoBuscado.get());
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarEndereco(@RequestBody @Valid EnderecoDto enderecoDto) {
        EnderecoModel Endereco = new EnderecoModel();
        BeanUtils.copyProperties(enderecoDto, Endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(Endereco));
    }

    @PutMapping(value = "/{idEndereco}")
    public ResponseEntity<Object> editarEndereco(@PathVariable(value = "idEndereco") UUID id, @RequestBody @Valid EnderecoDto enderecoDto) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        EnderecoModel endereco = enderecoBuscado.get();
        BeanUtils.copyProperties(enderecoDto, endereco);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoRepository.save(endereco));
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Object> deletarEndereco(@PathVariable(value = "idEndereco") UUID id) {
        Optional<EnderecoModel> enderecoBuscado = enderecoRepository.findById(id);

        if (enderecoBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado");
        }

        enderecoRepository.delete(enderecoBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereço deletado com sucesso!");
    }

}
