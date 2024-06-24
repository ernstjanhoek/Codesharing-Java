package platform.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import platform.utils.SnippetUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CodeSnippet {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;
    @NonNull
    @Column(length = Integer.MAX_VALUE)
    private String code;
    @NonNull
    private Date date;
    @NonNull
    private int timeLimit; /** time in seconds indicating how long snippet is accessible */
    @Column(columnDefinition = "integer default 0")
    private int views;
    @NonNull
    private int viewLimit;

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public Date getDateAsDate() {
        return this.date;
    }

    public void updateViews() {
        views++;
    }

    public boolean validate() {
        return (viewLimit == 0 || views < viewLimit ) &&
                (timeLimit == 0 || isWithinTimeLimit());
    }

    private boolean isWithinTimeLimit() {
        return SnippetUtils.calculateRemainingTimeInSeconds(this.date, this.timeLimit) > 0;
    }

    public long getRemainingTimeInSeconds() {
        return SnippetUtils.calculateRemainingTimeInSeconds(this.date, timeLimit);
    }

    public int getRemainingViews() {
        return Math.max(0, this.viewLimit - this.views);
    }
}