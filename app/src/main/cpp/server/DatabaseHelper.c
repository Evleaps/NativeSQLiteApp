#include <android/log.h>
#include <stdint.h>
#include <malloc.h>
#include <string.h>
#include <errno.h>
#include "DatabaseHelper.h"
#include "../sqlite/sqlite3.h"

char *err = 0;

/**
 * The current connection to database.
 */
sqlite3 *db;

/**
 * @param dbPath - absolute path to the opened database. Should be get on Android level.
 *
 * Create connection to db
 */
int connectToDatabase(char *dbPath) {
    if (sqlite3_open_v2(dbPath, &db, SQLITE_OPEN_READWRITE | SQLITE_OPEN_CREATE, NULL)) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "Error opened database: %s\n",
                            sqlite3_errmsg(db));
        return ERROR;
    }
    __android_log_print(ANDROID_LOG_INFO, TAG, "Create database:\n\r");
    return SUCCESSFULLY;
}

/**
 * Insert one string
 */
int insert(char *firstName, char *lastName) {
    const char *SQL;
    if (asprintf(
            &SQL, "INSERT INTO `addressBook` (`firstName`, `lastName`) VALUES ('%s', '%s')", firstName, lastName) < 0) {
        SQL = strdup(strerror(errno));
    }

    if (sqlite3_exec(db, SQL, 0, 0, &err)) {
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "Error SQL query: %s\n", err);
        sqlite3_free(err);
        return ERROR;
    }
    return SUCCESSFULLY;
}

/**
 * TODO: group insert if the number of strings more then 100
 */
int insertAll() {
    return 0;
}

/**
 * Destroy the current connection to database.
 */
int disconnectDatabase() {
    if (sqlite3_close(db) < 0) {
        return ERROR;
    }
    return SUCCESSFULLY;
}
