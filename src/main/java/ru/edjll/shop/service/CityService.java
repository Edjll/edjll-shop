package ru.edjll.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.edjll.shop.domain.City;
import ru.edjll.shop.repository.CityRepository;
import ru.edjll.shop.repository.CountryRepository;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    public void save(ru.edjll.shop.model.City city) {
        City cityDomain = new City();
        cityDomain.setName(city.getName());
        cityDomain.setCountry(countryRepository.getOne(city.getCountry()));
        cityRepository.save(cityDomain);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCity(Long id) {
        return cityRepository.getOne(id);
    }

    public void deleteCity(Long id) {
        City city = cityRepository.getOne(id);
        city.setCountry(null);
        cityRepository.delete(city);
    }

    public Page<City> getPageCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public City getCityByName(String name) {
        return cityRepository.getCityByName(name);
    }

    public void update(ru.edjll.shop.model.City city) {
        City cityDomain = cityRepository.getOne(city.getId());
        cityDomain.setName(city.getName());
        cityDomain.setCountry(countryRepository.getOne(city.getCountry()));
        cityRepository.save(cityDomain);
    }
}
