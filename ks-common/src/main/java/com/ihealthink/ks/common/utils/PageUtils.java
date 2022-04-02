package com.ihealthink.ks.common.utils;


import com.github.pagehelper.PageHelper;
import com.ihealthink.ks.common.utils.sql.SqlUtil;
import com.ihealthink.ks.common.web.page.PageDomain;
import com.ihealthink.ks.common.web.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author xiaoyang
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }
}
