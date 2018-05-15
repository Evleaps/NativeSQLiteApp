//
// Created by RAymaletdin on 4/17/2018.
//

#ifndef NATIVESERVER_DATABASEHELPER_H
#define NATIVESERVER_DATABASEHELPER_H

#define ERROR -1
#define SUCCESSFULLY 0
#define TAG "TAG_NATIVE_SQL"

int connectToDatabase(char *dbPath);
int insert(char* firstName, char* lastName);
int destroyDatabase();


#endif //NATIVESERVER_DATABASEHELPER_H
