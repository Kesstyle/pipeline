package by.minsk.kes.pipeline.persistence;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.SQLDialect.POSTGRES_10;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ContextProvider {

    private static final Logger LOG = getLogger(ContextProvider.class);

    @Value("${event.url}")
    private String url;

    @Value("${event.user}")
    private String user;

    @Value("${event.password}")
    private String password;

    @Value("${event.pool.max}")
    private int maxConnections;

    private DataSource dataSource;

    public DSLContext getContext() {
        final Connection connection = getConnection();
        final DSLContext ctx = DSL.using(connection, POSTGRES_10);
        return ctx;
    }

    private Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (final SQLException e) {
            LOG.error("Cannot get SQL connection: " + e);
        }
        return null;
    }

    private DataSource getDataSource() {
        if (dataSource == null) {
            try {
                DriverManager.registerDriver(new Driver());
            } catch (final SQLException e) {
                LOG.error("Cannot register driver: " + e);
                return null;
            }
            final ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);
            final PoolableConnectionFactory poolFactory = new PoolableConnectionFactory(connectionFactory, null);
            final GenericObjectPool connectionPool = new GenericObjectPool(poolFactory);
            connectionPool.setMaxTotal(maxConnections);
            poolFactory.setPool(connectionPool);
            dataSource = new PoolingDataSource(connectionPool);
        }
        return dataSource;
    }
}
