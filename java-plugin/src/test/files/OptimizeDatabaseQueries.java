class OptimizeDatabaseQueries {

    OptimizeDatabaseQueries(OptimizeDatabaseQueries mc) {
    }

    public void literalSQLrequest() {
        dummyCall("SELECT user FROM myTable"); // Noncompliant {{Optimize Database Queries (Clause LIMIT)}}
        dummyCall("SELECT user FROM myTable LIMIT 50"); // Compliant
    }

    private void dummyCall(String request) {

    }
}