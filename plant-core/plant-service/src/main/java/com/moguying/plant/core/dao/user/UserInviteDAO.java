package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.user.UserInvite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * UserInviteDAO继承基类
 */
@Repository
public interface UserInviteDAO extends BaseMapper<UserInvite> {
    Integer incUserInviteInfo(UserInvite incInfo);
    BigDecimal sumInviteAmount(@Param("field") String field, @Param("inviteUserId") Integer inviteUserId);
    Integer countInvite(@Param("inviteUserId") Integer inviteUserId);
    IPage<UserInvite> inviteList(Page<UserInvite> page, @Param("inviteUserId") Integer inviteUserId);
}