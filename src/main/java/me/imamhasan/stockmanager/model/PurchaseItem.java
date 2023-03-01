package me.imamhasan.stockmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_items")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Product is required.")
    private Product product;

    @OneToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Purchase is required.")
    private Purchase purchase;

    @NotNull(message = "Quantity Purchased is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    @Column(name = "quantity_purchased", nullable = false)
    private Integer quantityPurchased;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}
