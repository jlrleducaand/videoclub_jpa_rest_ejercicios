package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    private String texto;

    @ManyToOne
    @JoinColumn(name = "tutorial_id_fk", nullable = false,referencedColumnName = "id_tutorial",
            foreignKey = @ForeignKey(name = "FK_TUTO"))
    @ToString.Exclude
    Tutorial tutorial;


}
