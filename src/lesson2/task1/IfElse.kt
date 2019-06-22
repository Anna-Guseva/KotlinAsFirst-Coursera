@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    return when {
        age % 100 in 11..14 || age % 10 in 5..9 || age % 10 == 0 -> "$age лет"
        age % 10 == 1 -> "$age год"
        else -> "$age года"
    }
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    /*Case 1(without cycles)*/
//    val t1v1Length = t1 * v1
//    val t2v2Length = t2 * v2
//    val t3v3Length = t3 * v3
//    val halfWayLength = (t1v1Length + t2v2Length + t3v3Length) / 2
//    var resultTime = 0.0
//    when {
//        t1v1Length > halfWayLength -> resultTime = halfWayLength/v1
//        t1v1Length == halfWayLength -> resultTime = t1
//        else -> {
//            resultTime += t1
//            val restAfterT1 = halfWayLength - t1v1Length
//            when {
//                t2v2Length > restAfterT1 -> resultTime += restAfterT1/v2
//                t2v2Length == restAfterT1 -> resultTime += t2
//                else -> {
//                    resultTime += t2
//                    val restAfterT2 = restAfterT1 - t2v2Length
//                    when {
//                        t3v3Length > restAfterT2 -> resultTime += restAfterT2/v3
//                        else -> Double.NaN
//                    }
//                }
//            }
//        }
//    }
    /*Case 2 (doesn't depend on intervals count)*/
    val wayLengths = arrayOf(t1 * v1, t2 * v2, t3 * v3)
    val v = arrayOf(v1, v2, v3)
    val t = arrayOf(t1, t2, t3)
    var restWayLength = wayLengths.sum() / 2
    var resultTime = 0.0
    var i = 0
    while (i < wayLengths.size && restWayLength > 0.0) {
        when {
            wayLengths[i] > restWayLength -> {
                resultTime += restWayLength / v[i]
                restWayLength = 0.0
            }
            wayLengths[i] == restWayLength -> {
                resultTime += t[i]
                restWayLength = 0.0
            }
            else -> {
                resultTime += t[i]
                restWayLength -= wayLengths[i]
            }
        }
        i += 1
    }

    return resultTime;
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var res = 0
    if (rookX1 == kingX || rookY1 == kingY) res = 1
    if (rookX2 == kingX || rookY2 == kingY) {
        if (res == 1) res = 3
        else res = 2
    }
    return res
}


/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    var res = 0
    if (kingX == rookX || kingY == rookY) res = 1
    if (Math.abs(kingX - bishopX) == Math.abs(kingY - bishopY)) {
        if (res == 1) res = 3
        else res = 2
    }
    return res
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    if ((a < b + c) && (b < a + c) && (c < a + b)){
        val aPow2 = Math.pow(a, 2.0)
        val bPow2 = Math.pow(b, 2.0)
        val cPow2 = Math.pow(c, 2.0)
        return when {
            (aPow2 < bPow2 + cPow2) && (bPow2 < aPow2 + cPow2) && (cPow2 < aPow2 + bPow2) -> 0
            (aPow2 == bPow2 + cPow2) || (bPow2 == aPow2 + cPow2) || (cPow2 == aPow2 + bPow2) -> 1
            else -> 2
        }
    }
    return -1
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    return when {
        b < c || a > d -> -1
        c >= a && b >= d -> d - c
        c <= a && b <= d -> b - a
        c >= a && b <= d -> b - c
        c <= a && b >= d -> d - a
        else -> -1
    }
}
