package ru.edjll.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edjll.shop.domain.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage getStorageByName(String name);
}
