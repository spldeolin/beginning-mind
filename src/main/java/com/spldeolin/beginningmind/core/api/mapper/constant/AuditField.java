package com.spldeolin.beginningmind.core.api.mapper.constant;

import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/09
 */
@Log4j2
public class AuditField {

    public static final String INSERTED_AT_COLUMN_NAME = "inserted_at";

    public static final String UPDATED_AT_COLUMN_NAME = "updated_at";

    public static final String DELETION_FLAG_COLUMN_NAME = "deletion_flag";

    public static final String IS_NOT_DELETED = " " + DELETION_FLAG_COLUMN_NAME + " =-1 ";

    public static final String INSERTED_AT_FIELD_NAME = "loggedAt";

    public static final String UPDATED_AT_FIELD_NAME = "updatedAt";

    public static final String DELETION_FLAG_FIELD_NAME = "deletionFlag";

}
