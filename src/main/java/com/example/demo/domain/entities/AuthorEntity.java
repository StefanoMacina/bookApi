package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authors")
@SequenceGenerator(name = "seq",initialValue = 1,allocationSize = 1)
public class AuthorEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long authors_id;

    private String name;

    private Integer age;
}
