package com.company.repository;

import com.company.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer,Integer> {
    @Query("SELECT d FROM Developer d WHERE d.age= :age")
    List<Developer> findByAge(@Param("age") int age);

    @Query(value = "SELECT * FROM Developer",nativeQuery = true)
    List<Developer> findAllDeveloper();

    @Query(value = "SELECT * FROM developers WHERE developer_id IS NULL OR developer_id = ''", nativeQuery = true)
    List<Developer> findDevelopersWithoutId();

    @Modifying
    @Transactional
    @Query(value = "UPDATE developers SET developer_id = :developerId WHERE id = :id", nativeQuery = true)
    void updateDeveloperId(@Param("id") int id, @Param("developerId") String developerId);

    // ✅ Custom JPQL query to increment age if today is developer's birthday
    @Modifying
    @Transactional
    @Query("UPDATE Developer d SET d.age = d.age + 1 " +
            "WHERE MONTH(d.dob) = MONTH(CURRENT_DATE) " +
            "AND DAY(d.dob) = DAY(CURRENT_DATE)")
    void updateAgeForBirthdayDevelopers();
}
