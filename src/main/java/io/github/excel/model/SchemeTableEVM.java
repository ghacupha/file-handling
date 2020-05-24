package io.github.excel.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class is added here as example row-definition for deserialization of the schemes.xslx
 * <p>
 * file in the resources.
 * <p>
 * Now it is expected that a smart dev will develop own models in his own custom package not here
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class SchemeTableEVM implements Serializable {

    private static final long serialVersionUID = 1341014428380735074L;

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String schemeCode;

    @ExcelCell(1)
    private String description;
}
