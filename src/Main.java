import methods.ClassRandomType;
import methods.ElseType;
import methods.EventHandler;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;
import java.util.Stack;

public class Main {

    public static double update = 1_000_000_000/1;//Обновление сейчас раз в секунду
    Stack<Object>eh = new Stack<>();

    public static void UseEvents(Object o){
        Class clazz = o.getClass();
        Method[]methods=clazz.getDeclaredMethods();
        for (Method m : methods) {
          Annotation[]annotations= m.getDeclaredAnnotations();
          for (Annotation a : annotations) {
                if (a.annotationType().equals(EventHandler.class)){
                    try {
                        m.invoke(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[]a){
        new Main().start();
    }

public void start(){
       eh.push(new ElseType());
       eh.push(new ClassRandomType());

        new Thread(()->{
            long tn = System.nanoTime();
            while(true){
            if ((System.nanoTime()-tn>=update)){


                for (Object o : eh) {
                    UseEvents(o);
                }

                tn=System.nanoTime();
            }}
        }).start();
}

}