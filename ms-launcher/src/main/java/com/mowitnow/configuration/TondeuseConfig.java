package com.mowitnow.configuration;

import com.mowitnow.adapters.GrilleJpaAdapter;
import com.mowitnow.adapters.TondeuseJpaAdapter;
import com.mowitnow.mappers.GrilleMapper;
import com.mowitnow.mappers.TondeuseMapper;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.api.TondeuseService;
import com.mowitnow.ports.service.GrilleServiceImpl;
import com.mowitnow.ports.service.TondeuseServiceImpl;
import com.mowitnow.ports.spi.GrillePersistence;
import com.mowitnow.ports.spi.TondeusePersistence;
import com.mowitnow.repositories.GrilleRepository;
import com.mowitnow.repositories.TondeuseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TondeuseConfig {
    private final GrilleRepository grilleRepository;
    private final GrilleMapper grilleMapper;
    private final TondeuseRepository tondeuseRepository;
    private final TondeuseMapper tondeuseMapper;

    public TondeuseConfig(GrilleRepository grilleRepository,
                          GrilleMapper grilleMapper,
                          TondeuseRepository tondeuseRepository,
                          TondeuseMapper tondeuseMapper) {
        this.grilleRepository = grilleRepository;
        this.grilleMapper = grilleMapper;
        this.tondeuseRepository = tondeuseRepository;
        this.tondeuseMapper = tondeuseMapper;
    }

    @Bean
    public GrillePersistence grillePersistence() {
        return new GrilleJpaAdapter(grilleRepository, grilleMapper);
    }

    @Bean
    public GrilleService grilleService() {
        return new GrilleServiceImpl(grillePersistence());
    }

    @Bean
    public TondeusePersistence tondeusePersistence() {
        return new TondeuseJpaAdapter(tondeuseRepository, tondeuseMapper);
    }

    @Bean
    public TondeuseService tondeuseService() {
        return new TondeuseServiceImpl(tondeusePersistence());
    }
}
