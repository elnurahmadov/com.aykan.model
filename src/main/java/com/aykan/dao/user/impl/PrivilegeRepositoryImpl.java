package com.aykan.dao.user.impl;

import com.aykan.dao.user.PrivilegeRepository;
import com.aykan.domain.user.Privilege;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class PrivilegeRepositoryImpl implements PrivilegeRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Privilege savePrivilege(Privilege privilege) {
        entityManager.persist(privilege);
        return privilege;
    }

    @Override
    public Privilege updatePrivilege(Privilege privilege) {
        Privilege updatedPrivilege = entityManager.merge(privilege);
        entityManager.flush();
        return updatedPrivilege;
    }

    @Override
    public Privilege deletePrivilege(Privilege privilege) {
        if (entityManager.contains(privilege)) {
            entityManager.remove(privilege);
            return privilege;
        }
        Privilege deletePrivilege = findPrivilegeById(privilege.getId());
        entityManager.remove(privilege);
        return privilege;
    }

    @Override
    public Privilege findPrivilegeById(Long id) {
        if(id == null){
            return null;
        }
        return entityManager.find(Privilege.class, id);
    }

    @Override
    public Privilege findPrivilegeByName(String privilegeName) {
        TypedQuery<Privilege> typedQuery=entityManager.createNamedQuery("Privilege.findByName", Privilege.class);
        return typedQuery.setParameter("privilegeName", privilegeName).getSingleResult();
    }

    @Override
    public List<Privilege> findAllPrivileges() {
        return entityManager.createNamedQuery("Privilege.findAllPrivileges", Privilege.class).getResultList();
    }
}
