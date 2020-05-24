package io.github.excel;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

/**
 * This is a very general deserilizer for excel file, allowing one to tweak setting via the poiji
 * <p>
 * options class. The type of class is also specified by the deserialization-class in the constructor.
 * <p>
 * Once setup all you have to do is call the deserialize method with the byte array of the excel file
 *
 * Please note this has been used with poiji version 1.20.0; the api might change in later versions
 *
 * @param <T> Class for which this deserializer generates a List from the excel file
 */
@Slf4j
public class DefaultExcelFileDeserializer<T> implements ExcelFileDeserializer<T> {

    private final Class<T> deserializationClass;
    private final PoijiOptions poijiOptions;

    public DefaultExcelFileDeserializer(final Class<T> deserializationClass, final PoijiOptions poijiOptions) {
        this.deserializationClass = deserializationClass;
        this.poijiOptions = poijiOptions;
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<T> deserialize(final byte[] excelFile) {
        InputStream fileInputStream = DeserializationUtils.getFileInputStream(excelFile);
        long time = System.currentTimeMillis();
        List<T> entries = Poiji.fromExcel(fileInputStream, PoijiExcelType.XLSX, deserializationClass, poijiOptions);
        long readTime = System.currentTimeMillis() - time;
        log.info("\n{} entries deserialized from file: in {} millis", entries.size(), readTime);
        return entries;
    }
}
