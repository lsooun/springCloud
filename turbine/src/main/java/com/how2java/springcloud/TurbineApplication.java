package com.how2java.springcloud;

import cn.hutool.core.net.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class TurbineApplication {
    public static void main(String[] args) {
        int port = 8021;
        if (!NetUtil.isUsableLocalPort(port)) {
            System.out.printf("端口%d被占用，无法启动%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(TurbineApplication.class).properties("server.port=" + port).run(args);
    }
}
