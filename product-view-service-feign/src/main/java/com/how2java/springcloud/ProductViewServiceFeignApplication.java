package com.how2java.springcloud;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductViewServiceFeignApplication {
    public static void main(String[] args) {
        int port;
        int defaultPort = 8010;
        int rabbitMQPort = 5672;
        if (NetUtil.isUsableLocalPort(rabbitMQPort)) {
            System.err.printf("未在端口%d 发现 rabbitMQ 服务，请检查 rabbitMQ 是否启动", rabbitMQPort);
            System.exit(1);
        }

        Future<Integer> future = ThreadUtil.execAsync(() -> {
            int p;
            System.out.println("请于5秒钟内输入端口号, 推荐  8010  超过5秒将默认使用 " + defaultPort);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String inputPort = scanner.nextLine();
                if (NumberUtil.isInteger(inputPort)) {
                    p = Convert.toInt(inputPort);
                    break;
                }

                System.err.println("只能是数字");
            }

            return p;
        });

        try {
            port = future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            port = defaultPort;
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.out.printf("端口%d被占用，无法启动%n", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ProductViewServiceFeignApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
