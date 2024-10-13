package com.example.entities.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class MutantDto {
    @NotNull
    private String name;

    @NotNull
    private String[] adn;

    private boolean isMutant;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;

    public void setIsMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }
}
