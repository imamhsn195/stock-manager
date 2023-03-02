package me.imamhasan.stockmanager.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 1000)
    private String description;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.01", message = "Purchase price should not be less than 0.01")
    private BigDecimal purchasePrice;

    @NotNull(message = "Sale price is required")
    @DecimalMin(value = "0.01", message = "Sale Price should not be less than 0.01")
    private BigDecimal salePrice;


    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_category_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Product category is required.")
    private ProductCategory productCategory;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;
}
