package com.epam.mjc;

import java.security.SignatureSpi;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        StringTokenizer full = new StringTokenizer(signatureString, "(/)");
        String firstPart = full.nextToken();
        StringTokenizer firstPartToken = new StringTokenizer(firstPart, " ");
        String methodName= null;
        String accessModifier= null;
        String returnType=null;
        if (firstPartToken.countTokens() == 3) {
            accessModifier = firstPartToken.nextToken();
            returnType = firstPartToken.nextToken();
            methodName = firstPartToken.nextToken();
        } else {
            returnType = firstPartToken.nextToken();
            methodName = firstPartToken.nextToken();
        }
        List<MethodSignature.Argument> argumentsResult = new ArrayList<>();
        if (full.hasMoreTokens()) {
            String args = full.nextToken();
            StringTokenizer arguments = new StringTokenizer(args, ",");
            while (arguments.hasMoreTokens()) {
                StringTokenizer each = new StringTokenizer(arguments.nextToken(), " ");
                String argType = each.nextToken();
                String argName = each.nextToken();
                argumentsResult.add(new MethodSignature.Argument(argType, argName));
            }
        }

        MethodSignature result = new MethodSignature(methodName, argumentsResult);
        result.setMethodName(methodName);
        result.setAccessModifier(accessModifier);
        result.setReturnType(returnType);
        return result;
    }
}
