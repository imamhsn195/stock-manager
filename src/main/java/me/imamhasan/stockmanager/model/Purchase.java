package me.imamhasan.stockmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Supplier is required.")
    private Supplier supplier;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}
