package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler<T> implements InvocationHandler {

    public T gameMessage;

    public RemoteInvocationHandler(T t){
        this.gameMessage = t;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 调用业务方法处理
        Object result = method.invoke(gameMessage, args);
        return result;
    }
}
