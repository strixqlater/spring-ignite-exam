package kr.daoko.ignite.dao;

import kr.daoko.ignite.model.City;
import kr.daoko.ignite.model.CityKey;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.stereotype.Repository;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = "City")
@Repository
public interface CityRepository extends IgniteRepository<City, CityKey> {
    public List<Cache.Entry<CityKey, City>> findAllByPopulationGreaterThanEqualOrderByPopulation(int population);

    @Query("SELECT city.name, MAX(city.population), country.name, country.GovernmentForm FROM country " +
            "JOIN city ON city.countrycode = country.code " +
            "GROUP BY city.name, country.name, country.GovernmentForm, city.population " +
            "ORDER BY city.population DESC LIMIT ?")
    public List<List<?>> findMostPopulatedCities(int limit);

    @Query("SELECT * FROM City WHERE id = ?")
    public Cache.Entry<CityKey, City> findById(int id);
}
