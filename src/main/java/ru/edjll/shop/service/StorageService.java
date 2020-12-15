package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Storage;
import ru.edjll.shop.repository.CityRepository;
import ru.edjll.shop.repository.StorageRepository;

import java.util.List;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private CityRepository cityRepository;

    public void save(ru.edjll.shop.model.Storage storage) {
        Storage storageDomain = new Storage();
        storageDomain.setName(storage.getName());
        storageDomain.setDescription(storage.getDescription());
        storageDomain.setCity(cityRepository.getOne(storage.getCity()));
        storageRepository.save(storageDomain);
    }

    public List<Storage> getAllStorages() {
        return storageRepository.findAll();
    }

    public Storage getStorage(Long id) {
        return storageRepository.getOne(id);
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public Page<Storage> getPageStorages(Pageable pageable) {
        return storageRepository.findAll(pageable);
    }

    public Storage getStorageByName(String name) {
        return storageRepository.getStorageByName(name);
    }

    public void update(ru.edjll.shop.model.Storage storage) {
        Storage storageDomain = storageRepository.getOne(storage.getId());
        storageDomain.setName(storage.getName());
        storageDomain.setDescription(storage.getDescription());
        storageDomain.setCity(cityRepository.getOne(storage.getCity()));
        storageRepository.save(storageDomain);
    }
}
