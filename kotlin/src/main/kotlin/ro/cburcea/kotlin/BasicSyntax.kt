package ro.cburcea.kotlin

fun main() {
    println(sum(4,5))
    println(sum2(4,5))
    println(printSum(4,5))
    println(printSum(4,5))
    println(printSum2(4,5))

    stringTemplates();
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum2(a: Int, b: Int) = a + b

fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}

// Unit return type can be omitted:
fun printSum2(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}

fun variables() {
    //Read-only local variables are defined using the keyword val. They can be assigned a value only once.
    val a: Int = 1  // immediate assignment
    val b = 2   // `Int` type is inferred
    val c: Int  // Type required when no initializer is provided
    c = 3       // deferred assignment

    // Variables that can be reassigned use the var keyword:
    var x = 5 // `Int` type is inferred
    x += 1

    /* The comment starts here
    /* contains a nested comment */
    and ends here. */
}

fun stringTemplates() {
    var a = 1
// simple name in template:
    val s1 = "a is $a"

    a = 2
// arbitrary expression in template:
    val s2 = "${s1.replace("is", "was")}, but now is $a"

    println(s2)
}

// Nullable values and null checks
fun parseInt(str: String): Int? {
    return null;
}

fun nullableValues(arg1: String, arg2: String) {
    val x = parseInt(arg1)
    val y = parseInt(arg2)

    // Using `x * y` yields error because they may hold nulls.
    if (x != null && y != null) {
        // x and y are automatically cast to non-nullable after null check
        println(x * y)
    }
    else {
        println("'$arg1' or '$arg2' is not a number")
    }
}

