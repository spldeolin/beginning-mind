/*
 * Generated by Cadeau Support.
 *
 * https://github.com/spldeolin/cadeau-support
 */

package com.spldeolin.beginningmind.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.dao.OrganizationMapper;
import com.spldeolin.beginningmind.core.dto.IdNameDTO;
import com.spldeolin.beginningmind.core.dto.OrganizationNodeDTO;
import com.spldeolin.beginningmind.core.dto.OrganizationTreeDTO;
import com.spldeolin.beginningmind.core.model.Organization;
import com.spldeolin.beginningmind.core.model.User2organization;
import com.spldeolin.beginningmind.core.redis.RedisCache;
import com.spldeolin.beginningmind.core.service.OrganizationService;
import com.spldeolin.beginningmind.core.service.User2organizationService;
import com.spldeolin.beginningmind.core.util.Nulls;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.Condition;

/**
 * “组织架构”业务实现
 *
 * @author Deolin 2018/8/28
 */
@Service
@Log4j2
public class OrganizationServiceImpl extends CommonServiceImpl<Organization> implements OrganizationService {

    public static final String ORGANIZATION_TREE_CACHE_KEY = "organization_tree";

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private User2organizationService user2organizationService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Long createEX(Organization organization) {
        if (searchOne("name", organization.getName()).isPresent()) {
            throw new ServiceException("组织架构名已被占用");
        }

        Long parentId = organization.getParentId();
        if (parentId == null || CoupledConstant.ROOT_ORGANIZATION_ID.equals(parentId)) {
            organization.setParentId(CoupledConstant.ROOT_ORGANIZATION_ID);
        } else {
            if (!get(parentId).isPresent()) {
                throw new ServiceException("父组织架构不存在或是已被删除");
            }
        }

        super.create(organization);

        // 组织架构树缓存
        redisCache.delete(ORGANIZATION_TREE_CACHE_KEY);
        return organization.getId();
    }

    @Override
    public Organization getEX(Long id) {
        return super.get(id).orElseThrow(() -> new ServiceException("组织架构不存在或是已被删除"));
    }

    @Override
    public void updateEX(Organization organization) {
        if (!isExist(organization.getId())) {
            throw new ServiceException("组织架构不存在或是已被删除");
        }

        Optional<Organization> exist = searchOne("name", organization.getName());
        if (exist.isPresent() && !exist.get().getId().equals(organization.getId())) {
            throw new ServiceException("组织架构名已被占用");
        }

        if (!super.update(organization)) {
            throw new ServiceException("组织架构数据过时");
        }

        // 组织架构树缓存
        redisCache.delete(ORGANIZATION_TREE_CACHE_KEY);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        if (ids.contains(CoupledConstant.EDEN_ORGANIZATION_ID)) {
            throw new ServiceException(
                    "初始组织架构[ " + get(CoupledConstant.EDEN_ORGANIZATION_ID).get().getName() + "]不允许被删除");
        }

        List<Organization> exist = super.list(ids);
        if (exist.size() == 0) {
            throw new ServiceException("选中的组织架构全部不存在或是已被删除");
        }

        Condition condition = new Condition(Organization.class);
        condition.createCriteria().andIn("parentId", ids);
        if (searchBatch(condition).size() > 0) {
            throw new ServiceException("部分组织架构下存在子组织架构，无法删除");
        }

        Condition condition2 = new Condition(User2organization.class);
        condition2.createCriteria().andIn("organizationId", ids);
        if (user2organizationService.searchBatch(condition2).size() > 0) {
            throw new ServiceException("部分组织架构下存在用户，无法删除");
        }

        super.delete(ids);

        // 组织架构树缓存
        redisCache.delete(ORGANIZATION_TREE_CACHE_KEY);
        return "操作成功";
    }

    @Override
    public Page<Organization> page(PageParam pageParam) {
        Condition condition = new Condition(Organization.class);
        condition.createCriteria();

        pageParam.startPage();
        return Page.wrap(organizationMapper.selectBatchByCondition(condition));
    }

    @Override
    public OrganizationTreeDTO tree() {
        OrganizationTreeDTO tree = redisCache.get(ORGANIZATION_TREE_CACHE_KEY);

        if (tree == null) {
            List<Organization> models = listAll();
            Map<Long, Integer> userCounts = user2organizationService.mapUserCounts();
            tree = new OrganizationTreeDTO(
                    listNodes(models, models.iterator(), CoupledConstant.ROOT_ORGANIZATION_ID, userCounts));
            redisCache.set(ORGANIZATION_TREE_CACHE_KEY, tree);
        }

        return tree;
    }

    private List<OrganizationNodeDTO> listNodes(List<Organization> models, Iterator<Organization> itr,
            Long parentId, Map<Long, Integer> userCounts) {
        List<OrganizationNodeDTO> nodes = Lists.newArrayList();
        while (itr.hasNext()) {
            Organization organization = itr.next();
            if (Objects.equals(organization.getParentId(), parentId)) {
                OrganizationNodeDTO node = OrganizationNodeDTO.fromModel(organization);
                node.setUserQuantity(Nulls.toZero(userCounts.get(organization.getId())));
                node.setChildren(Lists.newArrayList());
                nodes.add(node);
                itr.remove();
            }
        }
        // 递归children
        for (OrganizationNodeDTO node : nodes) {
            itr = models.iterator();
            node.setChildren(listNodes(models, itr, node.getId(), userCounts));
        }
        return nodes;
    }

    @Override
    public List<IdNameDTO> tiny() {
        List<IdNameDTO> dtos = new ArrayList<>();
        listAll().forEach(o -> dtos.add(IdNameDTO.builder().id(o.getId()).name(o.getName()).build()));
        return dtos;
    }

}