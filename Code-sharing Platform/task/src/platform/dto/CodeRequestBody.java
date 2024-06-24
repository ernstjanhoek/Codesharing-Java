package platform.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeRequestBody {
    private String code;
    private int time;
    private int views;
}