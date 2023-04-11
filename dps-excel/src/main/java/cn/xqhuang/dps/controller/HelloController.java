package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.service.impl.AuthorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("download")
@Slf4j
public class HelloController {

    @Resource
    private AuthorsService authorsService;

    @GetMapping("streamDownload")
    public void streamDownload(HttpServletResponse response)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        authorsService.streamDownload(response);
        stopWatch.stop();

        log.info("耗时" + stopWatch.getTotalTimeNanos());
    }

    @GetMapping("traditionDownload")
    public void traditionDownload(HttpServletResponse response)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        authorsService.traditionDownload (response);
        stopWatch.stop();

        log.info("耗时" + stopWatch.getTotalTimeNanos());
    }
}  