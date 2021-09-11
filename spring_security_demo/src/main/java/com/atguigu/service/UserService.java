package com.atguigu.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService implements UserDetailsService {

    static Map<String,com.atguigu.user.User> map = new HashMap<>();
    static {
        com.atguigu.user.User user1 = new com.atguigu.user.User();
        user1.setUsername("yx");
        user1.setPassword("$2a$10$Nwo/9dAfRDelLVp9uN9aDuS8Jb/umsrzZwacI5yBa3iH8OZjOyt9q");
        user1.setTelephone("111");

        com.atguigu.user.User user2 = new com.atguigu.user.User();
        user2.setUsername("yxx");
        user2.setPassword("$2a$10$4cV0gwaxlxiD106gQiAOlOHLfAvAMMT/MBPRO04vjSJehg8eGJHei");
        user2.setTelephone("222");
        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("form userName:  " + username);
        com.atguigu.user.User userInDb = map.get(username);

        if(userInDb == null){
            return null;
        }

//        String passwordInDb = "{noop}" + userInDb.getPassword();
        String passwordInDb = userInDb.getPassword();
        List<GrantedAuthority> lists = new ArrayList<>();
        lists.add(new SimpleGrantedAuthority("add"));
        lists.add(new SimpleGrantedAuthority("update"));
        lists.add(new SimpleGrantedAuthority("ROLE_admin"));
        return new User(username,passwordInDb,lists);
    }
}
