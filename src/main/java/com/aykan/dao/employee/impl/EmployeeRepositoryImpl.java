package com.aykan.dao.employee.impl;

import com.aykan.dao.employee.EmployeeRepository;
import com.aykan.domain.employee.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean saveEmployee(Employee employee) {
        entityManager.persist(employee);
        return true;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        if (entityManager.contains(employee)) {
            entityManager.remove(employee);
        } else {
            Employee deleteEmployee = findEmployeeById(employee.getEmployeeId());
            entityManager.remove(deleteEmployee);
        }
        return true;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return entityManager.merge(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long employeeId) {
        return entityManager.createNamedQuery("Employee.findFullById", Employee.class).setParameter("employeeId", employeeId).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findEmployees() {
        return entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countEmployee() {
        return entityManager.createNamedQuery("Employee.count", Long.class).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> betweenSalaryEmployees(int minSalary, int maxSalary) {
        return entityManager.createNamedQuery("Employee.betweenSalary", Employee.class)
                .setParameter("minSalary", minSalary)
                .setParameter("maxSalary", maxSalary)
                .getResultList();
    }

    @Override
    public List<Employee> findEmployeesFirstMax(int first, int max) {
        return entityManager.createNamedQuery("Employee.findAll", Employee.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }
}
