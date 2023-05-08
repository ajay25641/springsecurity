package com.easybytes.Modal;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="authorities")
public class Authority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
