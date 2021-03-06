package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserInvite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * UserInviteDAO继承基类
 */
@Repository
public interface UserInviteDAO extends BaseDAO<UserInvite> {
    Integer incUserInviteInfo(UserInvite incInfo);

    BigDecimal sumInviteAmount(@Param("field") String field, @Param("inviteUserId") Integer inviteUserId);

    Integer countInvite(@Param("inviteUserId") Integer inviteUserId);

    IPage<UserInvite> inviteList(Page<UserInvite> page, @Param("inviteUserId") Integer inviteUserId);
}