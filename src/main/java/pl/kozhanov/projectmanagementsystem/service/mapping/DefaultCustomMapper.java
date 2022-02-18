package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.metadata.ClassMapBuilder;

public abstract class DefaultCustomMapper<A, B> extends CustomMapper<A, B> {

    public void registerConfiguration(ClassMapBuilder<A, B> builder){
        builder.byDefault()
                .customize(this)
                .register();
    }
}
