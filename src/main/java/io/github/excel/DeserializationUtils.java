package io.github.excel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class will convert any byte-array into input-stream and might be useful for testing purposes
 *
 * This is also because the application uses byte-array format for excel files while the poiji library
 *
 * uses input-stream format
 */
public class DeserializationUtils {

    public static InputStream getFileInputStream(byte[] byteArray) {

        return new ByteArrayInputStream(byteArray);
    }
}
