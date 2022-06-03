fn main() {
    let value = "test";
    /* Noncompliant@+1 {{Don't use the query SELECT * FROM}}*/
    let value2 = "select * from database";

}