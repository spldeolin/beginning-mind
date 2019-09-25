package com.spldeolin.beginningmind;

import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.util.MultimapCollectors;
import lombok.extern.log4j.Log4j2;

/**
 * @author Administrator 2019/01/01
 */

@Log4j2
public class ListToMapTest {

    private List<UserEntity> users;

    @Before
    public void init() {
        users = Lists.newArrayList();
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("d");
        entity.setSerialNumber("d");
        users.add(entity);
        entity = new UserEntity();
        entity.setId(2L);
        entity.setName("c");
        entity.setSerialNumber("b");
        users.add(entity);
        entity = new UserEntity();
        entity.setId(3L);
        entity.setName("a");
        entity.setSerialNumber("b");
        users.add(entity);
        entity = new UserEntity();
        entity.setId(4L);
        entity.setName("a");
        entity.setSerialNumber("a");
        users.add(entity);
    }

    @Test
    public void uniqueField() {
        // okay
        Map<Long, UserEntity> usersById = Maps.uniqueIndex(users, UserEntity::getId);
        log.info(usersById);

        // java.lang.IllegalArgumentException: Multiple entries with same key:
        Map<String, UserEntity> usersByName = Maps.uniqueIndex(users, UserEntity::getName);
        log.info(usersByName);

    }

    @Test
    public void multiMap() {
        // okey
        Multimap<Long, UserEntity> usersById = users.stream().collect(MultimapCollectors.toMultimap(UserEntity::getId));
        log.info(usersById);

        // okey
        Multimap<String, UserEntity> usersByName = users.stream()
                .collect(MultimapCollectors.toMultimap(UserEntity::getName));
        log.info(usersByName);
    }

}