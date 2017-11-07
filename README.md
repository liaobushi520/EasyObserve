# EasyObserve
简化了UI控制器（Activity）,观察ViewModel层数据的方式。
#
## 使用
```java
//在ViewModel中声明可以被观察的数据
@Observable("user")
public MediatorLiveData<User> userLiveData = new MediatorLiveData<>();

//在Activity中定义数据变化后，被通知的方法
@Observe("user")
public void observeUser(User user) {
        binding.setUser(user);
}
//在onCreate中
@Override
protected void onCreate(Bundle savedInstanceState) {       
        EasyObserve.observe(this);
} 
```
 
理念
google官方新出的组件框架，规定了Activity只是作为UI控制器，与业务逻辑相关的代码，应该放在ViewModel中。基于这一理念，我们可以认为Activity只需要关心他所关心的数据
至于这些数据从何而来，Activity可以无视。EasyObserve用注解的方式，让ViewModel他可以被观察的数据，所有关心这一数据的Activity只需要添加数据接收方法就可以了。

