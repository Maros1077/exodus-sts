package cz.exodus.sts.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TOKEN")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    Long id;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private ClientEntity client;

    @Column(name = "SCOPES", nullable = false)
    private String scopes;

    @Column(name = "EXPIRATION", nullable = false)
    private Date expiration;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "JTI", nullable = false)
    private String jti;

    @Column(name = "METADATA")
    private String metadata;

}
