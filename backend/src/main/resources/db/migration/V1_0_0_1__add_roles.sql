DO
$$
    BEGIN
        IF EXISTS(SELECT
                  FROM information_schema.tables
                  WHERE table_name = 'role'
                    AND table_schema = 'public')
        THEN
            INSERT INTO role(name)
            VALUES ('USER'),
                   ('ADMIN');
        END IF;
    END;
$$