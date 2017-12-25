package note.kafka;

/**
 * Created by Jary on 2017/12/22 0022.
 */
public class JSScript {


    /**
     * 求给定双精度数组中值的和
     */
    final public static String Sum =
            "function basisFuc(){ " +
                    "var sum = 0;  " +
                    "for(var i=0;i<arguments.length;++i) " +
                    "sum += arguments[i];" +
                    "return sum;" +
                    "} ";
    /**
     * 求给定双精度数组中值的平方和
     */
    final public static String SquareSum =
            "function basisFuc(){" +
                    "  if(arguments===null||arguments.length===0)" +
                    " return -1;" +
                    "  var len = arguments.length;" +
                    " var sqrsum = 0.0;" +
                    "  for (var i = 0; i <len; i++) {" +
                    "sqrsum = sqrsum + arguments[i] * arguments[i];" +
                    " }" +
                    " return sqrsum;" +
                    "}";
    /**
     * 求给定双精度数组中值的 平均值
     */
    final public static String Average =
            "function basisFuc(){" +
                "if(arguments===null||arguments.length===0)" +
                    "return -1;" +
                "var sum = 0;  " +
                "var len = arguments.length;" +
                "for(var i=0;i<arguments.length;++i) " +
                    "sum += arguments[i]; " +
            "return sum/len;}";

    /**
     * 求给定双精度数组中值的 方差
     */

    final public static String Variance =
            "function basisFuc(){" +
                    "if(arguments===null||arguments.length===0)" +
                    "return -1;" +
                    "var sum = 0;  " +
                    "var len = arguments.length;" +
                    "for(var i=0;i<arguments.length;++i) " +
                    "sum += arguments[i]; " +
                    "var average = sum/len;"+
                    "for(var i=0;i<arguments.length;++i) " +
                    "sumAV =+ (arguments[i]-average)*(arguments[i]-average);" +
                    "result = sumAV/len;"+
                    "return result;}";

}
