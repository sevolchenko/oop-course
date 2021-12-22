package ru.vsu.cs.volchenko_s_a;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        begin();
    }

    public static void begin() throws InvocationTargetException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type name of method");
        String methodName = sc.nextLine();
        List<Method> methods = Arrays.stream(CustomString.class.getMethods())
                .filter(method -> method.getName().equals(methodName))
                .collect(Collectors.toList());
        if (methods.size() == 0) {
            System.out.println("No methods with this name. Try again.");
            begin();
        } else {
            AtomicInteger methodIndex = new AtomicInteger(-1);
            methods.forEach(method -> {
                System.out.print(methodIndex.incrementAndGet() + ": ");
                AtomicInteger indexInParams = new AtomicInteger(-1);
                if (((method.getModifiers() & 8) == 8)) {
                    System.out.print("static ");
                }
                System.out.print(method.getReturnType().getSimpleName() + " ");
                System.out.print(methodName + "(");
                Arrays.stream(method.getParameterTypes())
                        .forEach(param -> {
                            if (indexInParams.incrementAndGet() > 0) {
                                System.out.print(", ");
                            }
                            System.out.print(param.getSimpleName());
                        });
                System.out.println(")");
            });
            System.out.println("Choose method number: ");
            int indexOfMethod = Integer.parseInt(sc.nextLine());
            if (indexOfMethod >= methods.size() || indexOfMethod < 0) {
                System.out.println("Wrong index.");
                begin();
                return;
            }
            Method method = methods.get(indexOfMethod);
            CustomString thisVar = null;
            if (((method.getModifiers() & 8) != 8)) {
                System.out.println("Type 'this' variable");
                thisVar = new CustomString(sc.next());
            }
            List<Object> params = new ArrayList<>();
            AtomicInteger indexInParams = new AtomicInteger(0);
            Arrays.stream(method.getParameterTypes())
                    .forEach(param -> {
                        System.out.println("Type value of parameter " + indexInParams.getAndIncrement() + " (" + param.getSimpleName() + ")");
                        if (param.isArray()) {
                            sc.nextLine();
                            String val = sc.nextLine();
                            params.add(castCharArray(param, val));
                        } else {
                            String val = sc.next();
                            params.add(castVariable(param, val));
                        }
                    });
            Object res = method.invoke(thisVar, params.toArray());
            System.out.println("Answer: " + res.toString());
        }
    }

    private static Object castVariable(Class<?> clz, String val) {
        if (clz.equals(String.class)) {
            return val;
        }
        if (clz.equals(CustomString.class)) {
            return new CustomString(val);
        }
        if (clz.equals(Character.class) || clz.equals(char.class)) {
            return val.charAt(0);
        }
        if (clz.equals(Integer.class) || clz.equals(int.class)) {
            return Integer.parseInt(val);
        }
        return null;
    }

    private static char[] castCharArray(Class<?> clz, String val) {
        if (clz.isArray()) {
            String[] strings = val.split("(\\s|[,;])+");
            Object[] objArr = Arrays.stream(strings)
                    .filter(string -> {
                        return string.length() == 1;
                    })
                    .map(string -> {
                        return string.charAt(0);
                    }).toArray();
            char[] res = new char[objArr.length];
            for (int index = 0; index < objArr.length; index++) {
                res[index] = (char) objArr[index];
            }
            return res;
        }
        return null;
    }
}
