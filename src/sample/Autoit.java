package sample;

/**
 * Created by Suzana on 14/11/2016.
 */
public class Autoit
{


    public String stringBetween(String inputStr,char StartChar,char EndChar){
if (inputStr==null) {
    return "NULL";
}
else
{
    inputStr = inputStr.substring(inputStr.indexOf(StartChar) + 1);
    inputStr = inputStr.substring(0, inputStr.indexOf(EndChar));

    return inputStr;

}
    }

    public String[] StringSplit(String InputString,String Delimeter){
        String[] splitedStringArray = InputString.split(Delimeter);

        return splitedStringArray;

    }

public  String StringTrimLeft(String inputStr,int HowManyCharsToDelete) {
            String part2= inputStr.substring(HowManyCharsToDelete + 1);
return  part2;
}

    public  String StringTrimRight(String inputStr,int HowManyCharsToDelete) {
        String part1="";
try {
     part1 = inputStr.substring(0, inputStr.length() - HowManyCharsToDelete);
}
catch (StringIndexOutOfBoundsException ex)
{

}

        return  part1;
    }


}
