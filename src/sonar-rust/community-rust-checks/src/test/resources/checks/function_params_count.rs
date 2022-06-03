

fn nine_params(_p1 : i32,_p2 : i32,_p3 : i32,_p4 : i32,_p5 : i32,_p6 : i32,_p7 : i32,_p8 : i32,_p9 : i32) -> i32 {/* Noncompliant {{Reduce the number of parameters that this function takes from 9 to at most 8.}} */
    return 42;
}