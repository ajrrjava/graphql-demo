package com.strateknia.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "create")
public class CreateDatabase implements Callable<Integer> {
    private static final Logger log = LoggerFactory.getLogger(CreateDatabase.class);

    @ParentCommand
    private String name;

    @ParentCommand
    private DataSource dataSource;

    @Override
    public Integer call() {
        log.info("Preparing to create database {}", name);
        return createDatabase();
    }

    private Integer createDatabase() {
        try {
            Connection connection = dataSource.getConnection();

            List<String> sqlStatements = List.of(
                    String.format("CREATE DATABASE %s", name),
                    String.format("USE %s",name)
            );

            for(String sql : sqlStatements) {
                boolean result = connection.prepareStatement(sql).execute();

                if (!result) {
                    log.error("Error occurred during creation of database {}.", name);
                    return ErrorCodes.DATABASE_ERROR.getCode();
                }
            }

            log.info("Database {} was created successfully!", name);

            connection.close();

            return ErrorCodes.NORMAL.getCode();
        } catch (SQLException e) {
            log.error("Caught: ", e);
            return ErrorCodes.DATABASE_ERROR.getCode();
        }
    }
}
