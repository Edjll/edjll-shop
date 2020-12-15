package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.Country;
import ru.edjll.shop.repository.CountryRepository;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }

    public Page<Country> getPageCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    public Country getCountryByName(String name) {
        return countryRepository.getCountryByName(name);
    }
}
