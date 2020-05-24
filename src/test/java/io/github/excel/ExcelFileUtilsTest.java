package io.github.excel;

import io.github.excel.model.ExcelDeserializerContainer;
import io.github.excel.model.SchemeTableEVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcelFileUtilsTest {

    private ExcelDeserializerContainer container;

    @BeforeEach
    void setUp() {
        container = new ExcelDeserializerContainer();
    }


    @Test
    public void deserializeSchemesFile() throws Exception {

        ExcelFileDeserializer<SchemeTableEVM> deserializer = container.schemeTableExcelFileDeserializer();

        // @formatter:off
        List<SchemeTableEVM> schemes =
            deserializer.deserialize(ExcelTestUtil.toBytes(ExcelTestUtil.readFile("schemes.xlsx")));
        // @formatter:on

        assertThat(schemes.size()).isEqualTo(3);
        assertThat(schemes.get(0)).isEqualTo(SchemeTableEVM.builder().rowIndex(1).schemeCode("scheme1").description("scheme1description").build());
        assertThat(schemes.get(1)).isEqualTo(SchemeTableEVM.builder().rowIndex(2).schemeCode("scheme2").description("scheme2description").build());
        assertThat(schemes.get(2)).isEqualTo(SchemeTableEVM.builder().rowIndex(3).schemeCode("scheme3").description("scheme3description").build());
    }
}
