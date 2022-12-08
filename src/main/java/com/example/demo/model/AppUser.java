package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    @Id
    private String id;
    @MapsId
    @JoinColumn(name = "identity_id")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Identity identity;
    private String firstName;
    private String lastName;

    public AppUser(Identity identity, String firstName, String lastName) {
        this.identity = identity;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
