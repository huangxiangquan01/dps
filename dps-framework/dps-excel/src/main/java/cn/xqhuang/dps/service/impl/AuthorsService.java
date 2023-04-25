package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.Authors;
import cn.xqhuang.dps.handler.CustomResultHandler;
import cn.xqhuang.dps.mapper.AuthorsMapper;
import cn.xqhuang.dps.process.DownloadProcessor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthorsService {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private AuthorsMapper authorsMapper;

    /**
     * stream读数据写文件方式
     * @param httpServletResponse
     * @throws IOException
     */
    public void streamDownload(HttpServletResponse httpServletResponse)
            throws IOException {
        HashMap<String, Object> param = new HashMap<>();
        CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(httpServletResponse));
        sqlSessionTemplate.select(
                "cn.xqhuang.dps.mapper.AuthorsMapper.streamByExample", param, customResultHandler);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

    /**
     * 传统下载方式
     * @param httpServletResponse
     * @throws IOException
     */
    public void traditionDownload(HttpServletResponse httpServletResponse)
            throws IOException {
        HashMap<String, Object> param = new HashMap<>();

        List<Authors> authors = authorsMapper.selectByExample(param);
        DownloadProcessor downloadProcessor = new DownloadProcessor (httpServletResponse);
        authors.forEach (downloadProcessor::processData);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}