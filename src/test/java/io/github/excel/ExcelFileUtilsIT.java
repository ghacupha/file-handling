package io.github.excel;

import io.github.excel.model.ExcelDeserializerContainer;
import io.github.excel.model.SchemeTableEVM;
import io.github.files.TestAppContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Running this test if nothing else might tell if the excel workflows are setup correctly
 * <p>
 * within the spring container's context. If it fails check carefully the spring container's
 * <p>
 * component-scan configuration especially if we are talking about spring-boot. In particular,
 * <p>
 * you can add this to the main class {@Code @ComponentScan(basePackages = {"io.github.excel",... }
 */
@SpringBootTest(classes = {ExcelDeserializerContainer.class, TestAppContainer.class})
public class ExcelFileUtilsIT {

    @Autowired
    private ExcelFileDeserializer<SchemeTableEVM> schemeTableExcelFileDeserializer;

    @Test
    public void deserializeSchemesFile() throws Exception {

        // @formatter:off
        List<SchemeTableEVM> schemes =
            schemeTableExcelFileDeserializer.deserialize(ExcelTestUtil.toBytes(ExcelTestUtil.readFile("schemes.xlsx")));
        // @formatter:on

        assertThat(schemes.size()).isEqualTo(3);
        assertThat(schemes.get(0)).isEqualTo(SchemeTableEVM.builder().rowIndex(1).schemeCode("scheme1").description("scheme1description").build());
        assertThat(schemes.get(1)).isEqualTo(SchemeTableEVM.builder().rowIndex(2).schemeCode("scheme2").description("scheme2description").build());
        assertThat(schemes.get(2)).isEqualTo(SchemeTableEVM.builder().rowIndex(3).schemeCode("scheme3").description("scheme3description").build());
    }
}
