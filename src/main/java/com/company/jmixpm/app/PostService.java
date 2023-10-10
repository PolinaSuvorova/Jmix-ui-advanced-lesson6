package com.company.jmixpm.app;

import com.company.jmixpm.entity.Post;
import com.company.jmixpm.entity.UserInfo;
import io.jmix.core.common.util.Preconditions;
import org.checkerframework.framework.qual.PreconditionAnnotation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {
    public List<Post> fetchPosts() {
        RestTemplate rest = new RestTemplate();
        Post[] posts = rest.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        return posts != null ? Arrays.asList(posts) : Collections.emptyList();
    }
    @NotNull
    public UserInfo fetchUserInfo(Long id){
        Preconditions.checkNotNullArgument(id);
        RestTemplate rest = new RestTemplate();
        return rest.getForObject("https://jsonplaceholder.typicode.com/users/{id}", UserInfo.class,id);
    }
}