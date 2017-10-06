package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Commissions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Commissions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionsRepository extends JpaRepository<Commissions, Long> {
    @Query("select distinct commissions from Commissions commissions left join fetch commissions.retailers")
    List<Commissions> findAllWithEagerRelationships();

    @Query("select commissions from Commissions commissions left join fetch commissions.retailers where commissions.id =:id")
    Commissions findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select commissions from Commissions commissions where commissions.description =:description")
    Commissions findCommissionsBySharedAndRetailers(@Param("description") String sharedDescription);
}
