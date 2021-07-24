package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.ProvideMoney;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.ProvideMoneyGetDetailVO;
import com.chards.committee.vo.ProvideMoneyGetParamVO;
import com.chards.committee.vo.ProvideMoneyPassVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-07-24
 */
public interface ProvideMoneyMapper extends BaseMapper<ProvideMoney> {

    List<ProvideMoney> getListWithParam(@Param("param") ProvideMoneyGetParamVO provideMoneyGetParamVO);

    @DataScope()
    Page<ProvideMoneyGetDetailVO> getFirstList(@Param("param") ProvideMoneyGetParamVO provideMoneyGetParamVO,
                                               @Param("page") Page<ProvideMoneyGetDetailVO> page);
    @DataScope()
    Page<ProvideMoneyGetDetailVO> getSecondList(@Param("param")ProvideMoneyGetParamVO provideMoneyGetParamVO,
                                                @Param("page")Page<ProvideMoneyGetDetailVO> page);

    Integer check(@Param("param") ProvideMoneyPassVO provideMoneyPassVO);
}
