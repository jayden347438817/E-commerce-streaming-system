package com.bupt.ecommercestreamingsystem;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///e_streaming_system";
        String username = "root";
        String password = "123456";
        String moduleName ="sys";
        String mapperLocation = "/Users/jayden/Desktop/Java/E-commerce-streaming-system/src/main/resources/mapper/" + moduleName;
        String tables = "users,products,orders,live_rooms,live_room_messages,comments";//根据表更改
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("Jayden") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/jayden/Desktop/Java/E-commerce-streaming-system/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.bupt.ecommercestreamingsystem") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables); // 设置需要生成的表名
                    //.addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}