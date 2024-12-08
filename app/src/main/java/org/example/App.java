package org.example;
import com.sun.tools.attach.VirtualMachine;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.*;
import net.bytebuddy.dynamic.*;
public class App {

    private static final int POOL_NORMAL = 0;
    private static final int POOL_SUSPENDED = 1;
    private static final int POOL_SHUTDOWN = 2;

    public volatile int poolState;

    public static void main(String[] args) {
        System.out.println("Running on:"+System.getProperty("java.version"));
        DynamicType.Builder<App> builder = new ByteBuddy().with(TypeValidation.ENABLED)
                .redefine(App.class)
                .name(App.class.getName() + "_Redefine");
        for (int i = 0; i < 100; i++) {
            builder.make();
        }
    }
}
