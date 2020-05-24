package io.github.excel;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;

import java.time.format.DateTimeFormatter;

/**
 * POIJI Configuration
 *
 * Please note this has been used with poiji version 1.20.0; the api might change in later versions
 */
public class PoijiOptionsConfig {

    public static PoijiOptions getDefaultPoijiOptions() {
        return PoijiOptionsHolder.getInstance();
    }

    /**
     * PoijiOptions is a configurations object. As it may appear to bewildered eye that the PoijiOptionsHolder
     * <p>
     * is an instance of over-engineering, but I would sooner ensure that I only have one PoijiOptions in the
     * <p>
     * entire applications because engineering for changes in this configuration would be too hard.
     * <p>
     * It is better to have an on demand initialization singleton that is also a double-locking checked
     * <p>
     * PoijiOptions as this object is used from reactive components running on Kafka generating messaging
     * <p>
     * events at any time during the application run, so there...
     */
    private static class PoijiOptionsHolder {
        private static volatile PoijiOptions INSTANCE;

        private static PoijiOptions getInstance() {
            if (INSTANCE == null) {
                synchronized (PoijiOptionsHolder.class) {
                    if (INSTANCE == null) {
                        // @formatter:off
                        INSTANCE = PoijiOptionsBuilder.settings()
                            .ignoreHiddenSheets(true)
                            .preferNullOverDefault(true)
                            .datePattern("yyyy/MM/dd")
                            .dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME)
                            .build();
                        // @formatter:on
                    }
                }
            }
            return INSTANCE;
        }
    }

}
