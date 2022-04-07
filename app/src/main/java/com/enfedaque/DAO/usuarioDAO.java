package com.enfedaque.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.enfedaque.domain.usuario;

@Dao
public interface usuarioDAO {

    //REgsitrar nuevo usuario
    @Insert
    void insert(usuario miUsuario);

    //Validar login
    @Query("SELECT pass FROM usuario WHERE email= :email")
    String findById(String email);
}
