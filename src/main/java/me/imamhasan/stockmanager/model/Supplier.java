package me.imamhasan.stockmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.imamhasan.stockmanager.repository.AddressRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email(message = "Email should be well formatted.")
    private String email;

    @NotBlank
    @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}", message=" The phone number should have the format (XXX)XXX-XXXX.")
    private String phone;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Supplier address is required.")
    private Address address;
}
