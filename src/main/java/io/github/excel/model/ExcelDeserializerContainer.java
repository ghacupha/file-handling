package io.github.excel.model;

import io.github.excel.DefaultExcelFileDeserializer;
import io.github.excel.ExcelFileDeserializer;
import io.github.excel.PoijiOptionsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This container contains sample definition of how bean definitions for using the poiji library
 * <p>
 * is used with spring to enable deserialization of excel files
 */
@Configuration
public class ExcelDeserializerContainer {

    @Bean("schemeTableExcelFileDeserializer")
    public ExcelFileDeserializer<SchemeTableEVM> schemeTableExcelFileDeserializer() {
        return excelFile -> new DefaultExcelFileDeserializer<>(SchemeTableEVM.class,
            PoijiOptionsConfig.getDefaultPoijiOptions()).deserialize(excelFile);
    }

}
