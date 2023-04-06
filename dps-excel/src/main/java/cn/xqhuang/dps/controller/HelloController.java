package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.service.impl.AuthorsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("download")
public class HelloController {
    private final AuthorsService authorsService;

    public HelloController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping("streamDownload")
    public void streamDownload(HttpServletResponse response)
            throws IOException {
        authorsService.streamDownload(response);
    }

    @GetMapping("traditionDownload")
    public void traditionDownload(HttpServletResponse response)
            throws IOException {
        authorsService.traditionDownload (response);
    }
}  