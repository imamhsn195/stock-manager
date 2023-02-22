package me.imamhasan.stockmanager.repository;

import me.imamhasan.stockmanager.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
