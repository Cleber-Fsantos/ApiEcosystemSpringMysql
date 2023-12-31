package com.senai.apiecosystem.controllers;

import com.senai.apiecosystem.dtos.AnuncioDto;
import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.repositories.AnuncioRepository;
import com.senai.apiecosystem.repositories.ProdutoRepository;
import com.senai.apiecosystem.repositories.UsuarioRepository;
import com.senai.apiecosystem.services.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/anuncio", produces = {"application/json"})
public class AnuncioController {
    @Autowired
    AnuncioRepository anuncioRepository;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Operation(summary = "Método para CONSULTAR todos os anuncios no sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<AnuncioModel>> listarAnuncios(){
        return ResponseEntity.status(HttpStatus.OK).body(anuncioRepository.findAll());
    }

    @Operation(summary = "Método para CONSULTAR um determinado anuncio especificando seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado")
    })
    @GetMapping("/{idAnuncio}")
    public ResponseEntity<Object> exibirAnuncio(@PathVariable(value = "idAnuncio") UUID id) {
        Optional<AnuncioModel> anuncioBuscado = anuncioRepository.findById(id);

        if (anuncioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anuncio não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(anuncioBuscado.get());
    }

    @Operation(summary = "Método para CADASTRAR um novo anuncio", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro de Anuncio com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> cadastrarAnuncio(@ModelAttribute @Valid AnuncioDto anuncioDto) {
        AnuncioModel anuncioModel = new AnuncioModel();
        BeanUtils.copyProperties(anuncioDto, anuncioModel);

        var usuario = usuarioRepository.findById(anuncioDto.id_usuario());
        var produto = produtoRepository.findById(anuncioDto.id_produto());

        LocalDate date = LocalDate.now();

        String urlImagem;
        try {
            urlImagem = fileUploadService.FazerUpload(anuncioDto.imagem());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        if (usuario.isPresent() && produto.isPresent()) {
            anuncioModel.setUsuario(usuario.get());
            anuncioModel.setProduto(produto.get());
            anuncioModel.setData_cadastro(date);
            anuncioModel.setUrl_imagem(urlImagem);

        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id_usuario ou id_produto não encontrado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(anuncioRepository.save(anuncioModel));
    }

    @Operation(summary = "Método para ALTERAR dados de um determinado anuncio especificando seu ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alteração de anuncio com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado")
    })
    @PutMapping(value = "/{idAnuncio}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> editarAnuncio(@PathVariable(value = "idAnuncio") UUID id, @ModelAttribute @Valid AnuncioDto anuncioDto) {
        Optional<AnuncioModel> anuncioBuscado = anuncioRepository.findById(id);

        if (anuncioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anuncio não encontrado");
        }

        AnuncioModel anuncio = anuncioBuscado.get();

        BeanUtils.copyProperties(anuncioDto, anuncio);

        String urlImagem;
        try {
            urlImagem = fileUploadService.FazerUpload(anuncioDto.imagem());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        anuncio.setUrl_imagem(urlImagem);

        return ResponseEntity.status(HttpStatus.CREATED).body(anuncioRepository.save(anuncio));
    }

    @Operation(summary = "Método para DELETAR um determinado Anuncio especificando seu ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anuncio deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anuncio não encontrado")
    })
    @DeleteMapping("/{idAnuncio}")
    public ResponseEntity<Object> deletarAnuncio(@PathVariable(value = "idAnuncio") UUID id) {
        Optional<AnuncioModel> anuncioBuscado = anuncioRepository.findById(id);

        if (anuncioBuscado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anuncio não encontrado");
        }

        anuncioRepository.delete(anuncioBuscado.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Anuncio deletado com sucesso!");
    }



}


