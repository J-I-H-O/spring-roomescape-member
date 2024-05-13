package roomescape.repository.dynamic;

import java.sql.Types;
import java.util.Arrays;
import roomescape.global.exception.ApplicationException;
import roomescape.global.exception.ExceptionType;

public enum Condition {

    MEMBER_ID("memberId", "member_id = ?", Types.BIGINT),
    THEME_ID("themeId", "theme_id = ?", Types.BIGINT),
    DATE_FROM("dateFrom", "? <= start_at", Types.DATE),
    DATE_TO("dateTo", "start_at <= ?", Types.DATE);

    private final String conditionName;
    private final String query;
    private final Integer type;

    Condition(String conditionName, String query, Integer type) {
        this.conditionName = conditionName;
        this.query = query;
        this.type = type;
    }

    public static Condition findConditionByName(String conditionName) {
        return Arrays.stream(values())
                .filter(condition -> condition.conditionName.equals(conditionName))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(ExceptionType.INVALID_QUERY_PARAMETER));
    }

    public String getConditionName() {
        return conditionName;
    }

    public String getQuery() {
        return query;
    }

    public Integer getType() {
        return type;
    }
}
