package me.imamhasan.stockmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale_items")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Product is required.")
    @JsonIgnoreProperties({"name", "description","product_category_id","purchase_price","sale_price"})
    private Product product;

    @OneToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Sale is required.")
    private Sale sale;

    @NotNull(message = "Quantity sold is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    @Column(name = "quantity_sold", nullable = false)
    private Integer quantitySold;

    @NotNull(message = "Price sold is required")
    @DecimalMin(value = "0.01", message = "Sale Price should not be less than 0.01")
    private BigDecimal priceSold;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

}
