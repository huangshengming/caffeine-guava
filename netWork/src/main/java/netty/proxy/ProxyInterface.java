package proxy;

public interface ProxyInterface {

    Object newOutPutProxy(Class<RemoteInvocationHandler> clazz);
    Object newSelfProxy(Class<SelfInvocationHandler> clazz);
    Object newInPutProxy(Class<ReplayInvocationHandler> clazz);
}
