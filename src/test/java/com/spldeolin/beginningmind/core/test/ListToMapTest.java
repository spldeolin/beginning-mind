package com.spldeolin.beginningmind.core.test;

import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.util.MultimapCollectors;
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
        users.add(UserEntity.builder().id(1L).name("d").serialNumber("d").build());
        users.add(UserEntity.builder().id(2L).name("c").serialNumber("b").build());
        users.add(UserEntity.builder().id(3L).name("a").serialNumber("b").build());
        users.add(UserEntity.builder().id(4L).name("a").serialNumber("a").build());
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
        Multimap<String, UserEntity> usersByName = users.stream().collect(MultimapCollectors.toMultimap(UserEntity::getName));
        log.info(usersByName);
    }

}