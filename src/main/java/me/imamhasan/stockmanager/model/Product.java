package me.imamhasan.stockmanager.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

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

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price should not be less than 0.01")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "product_category_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Product category is required.")
    private ProductCategory productCategory;

}
