package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

//@Aspect
//@Component
public class AOPAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object around(ProceedingJoinPoint joinPoint){
        //取消序列
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //拼接
        StringBuilder sb = new StringBuilder();
        //获取全路径
        String pathName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        sb.append(pathName).append(methodName);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        ValueOperations ops = redisTemplate.opsForValue();
        Object obj = null;
        //如果Redis中查出来的数据和数据库查出来的数据一致，执行if里面的代码，如果不一致执行放行请求
        if (redisTemplate.hasKey(sb.toString())){
            obj = ops.get(sb.toString());
        }else {
            try {
                //放行请求
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            ops.set(sb.toString(), obj);
        }

        return obj;
    }
    //执行增删改 清除缓存
    @After("@annotation(cn.baizhi.annotation.DeleteAspect)")
    public void delAspect(JoinPoint joinPoint){
        //类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            String newKey = (String)key;
            if (newKey.startsWith(className)){
                redisTemplate.delete(key);
            }
        }
    }
}
