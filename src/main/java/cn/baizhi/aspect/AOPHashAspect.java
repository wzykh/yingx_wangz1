package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AOPHashAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object around(ProceedingJoinPoint joinPoint){
        //取消序列
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
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
        HashOperations hash = redisTemplate.opsForHash();
        Object obj = null;
        //如果Redis中查出来的数据和数据库查出来的数据一致，执行if里面的代码，如果不一致执行放行请求
        if (hash.hasKey(pathName, sb.toString())){
            obj = hash.get(pathName, sb.toString());
        }else {
            try {
                //放行请求
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hash.put(pathName, sb.toString(), obj);
        }

        return obj;
    }
    //执行增删改 清除缓存
    @After("@annotation(cn.baizhi.annotation.DeleteAspect)")
    public void delAspect(JoinPoint joinPoint){
        //类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);
    }
}
