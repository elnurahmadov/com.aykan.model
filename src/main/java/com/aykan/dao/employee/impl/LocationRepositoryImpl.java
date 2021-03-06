package com.aykan.dao.employee.impl;

import com.aykan.dao.employee.LocationRepository;
import com.aykan.domain.employee.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class LocationRepositoryImpl implements LocationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean saveLocation(Location location) {
        entityManager.persist(location);
        return true;
    }

    @Override
    public boolean deleteLocation(Location location) {
        if (entityManager.contains(location)) {
            entityManager.remove(location);
        } else {
            Location deleteLocation = findLocationById(location.getLocationId());
            entityManager.remove(deleteLocation);
        }
        return true;
    }

    @Override
    public Location updateLocation(Location location) {
        Location updatedLocation = entityManager.merge(location);
        entityManager.flush();
        return updatedLocation;
    }

    @Override
    @Transactional(readOnly = true)
    public Location findLocationById(Long locationId) {
        return entityManager.createNamedQuery("Location.findDepartmentById", Location.class).setParameter("locationId", locationId).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> findAllLocation() {
        return entityManager.createNamedQuery("Location.findAll", Location.class).getResultList();
    }
}
