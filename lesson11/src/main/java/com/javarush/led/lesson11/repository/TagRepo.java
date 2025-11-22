package com.javarush.led.lesson11.repository;

import com.javarush.led.lesson11.model.tag.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TagRepo extends Repo<Tag> {

    Optional<Tag> findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM tbl_tag t WHERE t.id IN (:tagIdsToCheck) AND t.id NOT IN (SELECT tag_id FROM tbl_story_tag)", nativeQuery = true)
    int deleteOrphanTags(@Param("tagIdsToCheck") Collection<Long> tagIdsToCheck);
}
