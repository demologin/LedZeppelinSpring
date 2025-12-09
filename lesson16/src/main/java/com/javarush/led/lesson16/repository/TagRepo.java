package com.javarush.led.lesson16.repository;

import com.javarush.led.lesson16.model.tag.Tag;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface TagRepo extends Repo<Tag> {

    Mono<Tag> findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM tbl_tag t WHERE t.id IN (:tagIdsToCheck) " +
                   "AND t.id NOT IN (SELECT tag_id FROM tbl_story_tag)")
    Mono<Integer> deleteOrphanTags(@Param("tagIdsToCheck") Collection<Long> tagIdsToCheck);
}
