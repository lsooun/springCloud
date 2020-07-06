package com.how2java.springcloud;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductDataServiceApplication {
    public static void main(String[] args) {
        int port;
        int defaultPort = 8001;
        Future<Integer> future = ThreadUtil.execAsync(new Callable<>() {
            int p;

            @Override
            public Integer call() {
                System.out.println("请于5秒钟内输入端口号, 推荐  8001 、 8002  或者  8003，超过5秒将默认使用 " + defaultPort);
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
            }
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

        new SpringApplicationBuilder(ProductDataServiceApplication.class).properties("server.port=" + port).run(args);
    }

    @Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
