package com.schwarzit.cbctapi.models;

import com.schwarzit.cbctapi.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "products")
public class ProductsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public Double price;

    public int quantity;

    @Enumerated(EnumType.STRING)
    public Type type;
}
