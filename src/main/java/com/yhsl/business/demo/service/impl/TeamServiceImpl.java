package com.yhsl.business.demo.service.impl;

import com.yhsl.business.demo.entity.Team;
import com.yhsl.business.demo.mapper.TeamMapper;
import com.yhsl.business.demo.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouchao
 * @since 2019-12-11
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

}
