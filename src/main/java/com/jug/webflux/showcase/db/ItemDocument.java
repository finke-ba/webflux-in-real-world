package com.jug.webflux.showcase.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "items")
@TypeAlias("ItemDocument")
public class ItemDocument {
    @Id
    private String id;
    private String name;
    private String count;
    private String source;
}
