package io.github.excel;

import java.util.List;

/**
 * This generic interface represents operations for converting data contained in files into data that
 * <p>
 * can be saved in a db row by row from the data file.
 * <p>
 * Implement this interface and the client will now be able to read excel files into a list. It's easier
 * <p>
 * if you borrow code from the already useful default-excel-file-deserializer in this package
 *
 * @param <T> Type of data contained in the data file
 */
public interface ExcelFileDeserializer<T> {

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    List<T> deserialize(byte[] excelFile);
}
