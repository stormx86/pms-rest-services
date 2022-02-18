package pl.kozhanov.projectmanagementsystem.service.mapping;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.UtilityResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class OrikaBeanMapper extends ConfigurableMapper {
    static {
        System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "false");
        System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "false");
    }

    @Autowired(required = false)
    private List<DefaultCustomMapper> mappers = emptyList();

    @Autowired(required = false)
    private List<Converter> converters = emptyList();

    private MapperFactory mapperFactory;

    public OrikaBeanMapper() {
        super(false);
    }

    @Override
    protected void configure(MapperFactory factory) {
        this.mapperFactory = factory;
    }

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        ConverterFactory cf = UtilityResolver.getDefaultConverterFactory();
        converters.forEach(cf::registerConverter);
        factoryBuilder.converterFactory(cf);
    }

    @PostConstruct
    private void initCustomMappers() {
        super.init();
        mappers.forEach(this::registerMapper);
    }

    public void registerMapper(DefaultCustomMapper mapper) {
        mapper.registerConfiguration(mapperFactory.classMap(mapper.getAType(), mapper.getBType()));
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }
}
