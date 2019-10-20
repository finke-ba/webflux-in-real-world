package com.jug.webflux.showcase.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("UserDocument")
public class UserDocument {
    @Id
    private String id;
    private String name;
    private String password;
}
