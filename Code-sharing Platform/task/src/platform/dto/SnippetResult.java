package platform.dto;

import lombok.Data;
import platform.entity.CodeSnippet;
import platform.utils.SnippetUtils;
import java.util.Date;


@Data
public class SnippetResult {
    private String code;
    private Date date;
    /** Same name for field as in Snippet. But here time is remaining time before invalidation or zero*/
    private int time;
    /** Remaining allowed views before invalidation or zero*/
    private int views;

    public SnippetResult(CodeSnippet codeSnippet) {
        this.code = codeSnippet.getCode();
        this.date = codeSnippet.getDateAsDate();
        this.time = Math.max(0, (int) SnippetUtils.calculateRemainingTimeInSeconds(codeSnippet.getDateAsDate(), codeSnippet.getTimeLimit()));
        this.views = Math.max(0, codeSnippet.getViewLimit() - codeSnippet.getViews());
    }
}