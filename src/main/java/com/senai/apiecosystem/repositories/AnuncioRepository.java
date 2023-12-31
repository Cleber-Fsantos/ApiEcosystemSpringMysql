package com.senai.apiecosystem.repositories;

import com.senai.apiecosystem.models.AnuncioModel;
import com.senai.apiecosystem.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnuncioRepository extends JpaRepository<AnuncioModel, UUID> {

}
