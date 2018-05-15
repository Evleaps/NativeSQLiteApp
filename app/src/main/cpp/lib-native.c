#include <jni.h>
#include "server/Server.h"
#include "server/DatabaseHelper.h"

JNIEXPORT jint

JNICALL
Java_com_example_raymaletdin_nativeserver_MainActivity_startServer(
        JNIEnv *env,
        jobject thiz,
        jstring dbPath) {

    const char *nativeString = (*env)->GetStringUTFChars(env, dbPath, 0);
    connectToDatabase(nativeString);
    return startServer();
}

JNIEXPORT jint

JNICALL
Java_com_example_raymaletdin_nativeserver_MainActivity_destroyServer(
        JNIEnv *env,
        jobject thiz) {

    int result_db = destroyDatabase();
    int result_server = destroyServer();
    return result_db && result_server < 0 ? ERROR : SUCCESSFULLY;
}

