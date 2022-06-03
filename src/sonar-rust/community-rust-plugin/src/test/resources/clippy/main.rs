fn main() {
    println!("Checking issues");
    absurd_extreme_comparison();
    println!("Done");
}

fn absurd_extreme_comparison(){
    let vec: Vec<isize> = Vec::new();
    if vec.len() <= 0 {}
    if 100 > std::i32::MAX {}
}