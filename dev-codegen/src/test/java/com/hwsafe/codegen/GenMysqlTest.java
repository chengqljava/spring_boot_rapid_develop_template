package com.hwsafe.codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hwsafe.codegen.service.CodeGenService;


@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles(profiles = { "mysql" })
public class GenMysqlTest {

    @Autowired
    private CodeGenService codeGenService;


    @Test
    public void testGen() throws Exception {

        codeGenService.generatorCode("user");
    }

}
