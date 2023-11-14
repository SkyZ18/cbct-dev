package com.schwarzit.cbctapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "storage")
public class StorageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public int quantity;

    public String Description;

}
