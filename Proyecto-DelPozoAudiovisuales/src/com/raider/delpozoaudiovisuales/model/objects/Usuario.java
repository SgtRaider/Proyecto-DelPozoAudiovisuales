package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;

/**
 * Created by Raider on 01/11/2016.
 */
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="username")
    private String username;

    @Column(name="pass")
    private String pass;

    @Column(name="rol")
    private String rol;

}
