package org.spicher.brp.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spicher.brp.data.entity.Features;
import org.spicher.brp.data.entity.Projects;
import org.spicher.brp.data.service.FeaturesRepository;
import org.spicher.brp.data.service.ProjectsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ProjectsRepository projectsRepository, FeaturesRepository featuresRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (projectsRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Projects entities...");
            ExampleDataGenerator<Projects> projectsRepositoryGenerator = new ExampleDataGenerator<>(Projects.class,
                    LocalDateTime.of(2022, 9, 15, 0, 0, 0));
            projectsRepositoryGenerator.setData(Projects::setName, DataType.WORD);
            projectsRepositoryGenerator.setData(Projects::setPriority, DataType.NUMBER_UP_TO_10);
            projectsRepositoryGenerator.setData(Projects::setValue, DataType.NUMBER_UP_TO_10);
            projectsRepositoryGenerator.setData(Projects::setLead, DataType.FULL_NAME);
            projectsRepositoryGenerator.setData(Projects::setActiv, DataType.BOOLEAN_90_10);
            projectsRepository.saveAll(projectsRepositoryGenerator.create(100, seed));

            logger.info("... generating 100 Features entities...");
            ExampleDataGenerator<Features> featuresRepositoryGenerator = new ExampleDataGenerator<>(Features.class,
                    LocalDateTime.of(2022, 9, 15, 0, 0, 0));
            featuresRepositoryGenerator.setData(Features::setName, DataType.WORD);
            featuresRepositoryGenerator.setData(Features::setPriority, DataType.NUMBER_UP_TO_10);
            featuresRepositoryGenerator.setData(Features::setValue, DataType.NUMBER_UP_TO_10);
            featuresRepository.saveAll(featuresRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}
