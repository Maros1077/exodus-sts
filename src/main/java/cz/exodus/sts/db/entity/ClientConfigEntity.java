package cz.exodus.sts.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "CLIENT_CONFIG")
public class ClientConfigEntity {

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
    private int expiration;

    @Column(name = "TYPE", nullable = false)
    private String type;
}
