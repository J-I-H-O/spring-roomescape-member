package roomescape.repository.dynamic;

import java.util.Map;
import java.util.stream.Collectors;

public class ReservationFilterConditions {

    private final Map<Condition, Object> conditions;

    public ReservationFilterConditions(Map<Condition, Object> conditions) {
        this.conditions = conditions;
    }

    public String createDynamicQuery(String baseSql) {
        if (conditions.isEmpty()) {
            return baseSql;
        }
        return createQueryByConditions(baseSql);
    }

    private String createQueryByConditions(String baseSql) {
        String whereClauseConditions = conditions.keySet()
                .stream()
                .map(Condition::getQuery)
                .collect(Collectors.joining(" AND "));

        StringBuilder stringBuilder = new StringBuilder(baseSql);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(whereClauseConditions);

        return stringBuilder.toString();
    }

    public Object[] getArgs() {
        return conditions.values()
                .toArray();
    }

    public int[] getArgTypes() {
        return conditions.keySet()
                .stream()
                .mapToInt(condition -> condition.getType().intValue())
                .toArray();
    }
}
