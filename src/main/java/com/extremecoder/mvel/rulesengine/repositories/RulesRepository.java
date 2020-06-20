package com.extremecoder.mvel.rulesengine.repositories;

import com.extremecoder.mvel.rulesengine.models.RulesEntity;
import org.apache.tomcat.util.digester.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.READ_ONLY;
import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;


@Repository
public interface RulesRepository extends JpaRepository<RulesEntity,Long> {
    /* This allows us to return Stream from DB.
    @QueryHints(value = {
            @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE),
            @QueryHint(name = HINT_CACHEABLE, value = "false"),
            @QueryHint(name = READ_ONLY, value = "true")
    })
    @Query("select r from RulesEntity r where r.nameSpace=?1")*/

    /**
     *
     * @param ruleNamespace
     * @return
     */
    List<RulesEntity> findRulesEntitiesByNameSpace(String ruleNamespace);

    /**
     *
     * @param ruleId
     * @return
     */
    Optional<RulesEntity> findByRuleId(Long ruleId);

    /**
     *
     * @param identifier
     * @return
     */
    Optional<RulesEntity> findByIdentifier(String identifier);

    /**
     *
     * @param identifier
     * @param namespace
     * @return
     */
    Optional<RulesEntity> findByIdentifierAndAndNameSpace(String identifier, String namespace);

    /**
     *
     * @param entity
     * @return
     */
    RulesEntity saveAndFlush(RulesEntity entity);

    /**
     *
     * @param rules
     * @return
     */
    List<RulesEntity> save(Iterable<? extends RulesEntity> rules);

}
