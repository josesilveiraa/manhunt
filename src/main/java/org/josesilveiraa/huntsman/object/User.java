package org.josesilveiraa.huntsman.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor
public class User {
    private UUID uuid;
    private String name;
}
