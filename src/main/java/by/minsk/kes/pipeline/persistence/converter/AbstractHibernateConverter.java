package by.minsk.kes.pipeline.persistence.converter;

import org.springframework.stereotype.Component;

@Component
public abstract class AbstractHibernateConverter<DB, UI> {

    public abstract UI convertToUiModel(final DB user);

    public abstract DB convertToDbModel(final UI user);
}
