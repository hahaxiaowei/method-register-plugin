#Method-register-plugin 设计思路

> 这个plugin的主要目的是为了解放开发人员对于EDM中方法的配置动作，程序员专注于方法的开发，而不需要手动操作繁琐的配置
> 通过注解配置就能够在程序启动时，将所开发的方法自动注册到EDM中

## @MethodRegister 说明

> 此注解标注在方法级别，标注在需要进行EDM注册的方法上面，该方法应该写在对应的@Controller标注下的类中

### 注解选项说明  

**edmClass**：对应EDM中class的英文名称  
  
**methodType**： 方法的类型：SyncMethod（同步）, AsyncMethod（异步）, TimingMethod（定时）, AutoMethod（自动）

**methodExeFrequency**：方法执行平率：Once（一次），Loop（循环），只有methodType == AutoMethod，才起作用

**methodExeInterval**：方法执行间隔：默认5分钟，只有methodType == AutoMethod，才起作用

**timeOps**：定时方法选项，只有methodType == TimingMethod，此选项才起作用

### 使用方式说明

#### 在ServiceProvider中添加Config配置

```java

@Controller
@RequestMapping("/ruixin")
public class OneController {

    @RequestMapping(value = "/meeting", method= RequestMethod.GET)
    @MethodRegister(edmClass = "MeetingRoom",
            methodType = MethodType.SyncMethod,
            methodExeFrequency = MethodExeFrequency.Once,
            methodExeInterval = 10,
            timeOps = "* * * * * 1/5"
    )
    public String meeting(Date date, String address) {

        return "training room.";
    }
}

```

#### 方法注册示例

```java

@Controller
@RequestMapping("/ruixin")
public class OneController {

    @RequestMapping("/meeting")
    @MethodRegister(edmClass = "MeetingRoom",
            methodType = MethodType.SyncMethod,
            methodExeFrequency = MethodExeFrequency.Once,
            methodExeInterval = 10,
            timeOps = "* * * * * 1/5"
    )
    public String meeting(Date date, String address) {

        return "training room.";
    }
}

```

