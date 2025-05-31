package co.edu.poli.ces3.socrates.socrates.config;

import java.util.List;

public class QueryResult {
    private final String sql;
    private final List<Object> parameters;

    public QueryResult(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    // getters
    public String getSql() { return sql; }
    public List<Object> getParameters() { return parameters; }
}
