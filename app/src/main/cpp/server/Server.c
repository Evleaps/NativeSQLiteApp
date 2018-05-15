#include "Server.h"
#include "DatabaseHelper.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>
#include <android/log.h>

#define PORT 9845

int isWork = 1;

/**
 * Create TCP/IP connection
 *
 * @return ERROR if something went wrong will receive the server state for correct behavior
 * android level.
 */
int startServer() {
    int sock;
    int listener;
    int bytes_read;
    char buf[1024];
    struct sockaddr_in addr;

    listener = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (listener < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "The listener has not received the socket\n");
        return ERROR;
    }

    __android_log_print(ANDROID_LOG_DEBUG, TAG, "Listener created\n");

    addr.sin_family = AF_INET;
    addr.sin_port = htons(PORT);
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    if (bind(listener, (struct sockaddr *) &addr, sizeof(addr)) < 0) {
        __android_log_print(ANDROID_LOG_ERROR, TAG, "Bind error. The PORT is probably busy already.\n");
        return ERROR;
    }
    listen(listener, 1);
    isWork = 1;

    while (isWork) {
        sock = accept(listener, NULL, NULL);
        if (sock < 0) {
            __android_log_print(ANDROID_LOG_ERROR, TAG, "accept error.\n");
            return ERROR;
        } else {
            __android_log_print(ANDROID_LOG_INFO, TAG, "Accept connection\n");
        }

        while (isWork) {
            bytes_read = recv(sock, buf, 1024, 0);
            if (bytes_read <= 0) break;
            send(sock, buf, bytes_read, 0);
            __android_log_print(ANDROID_LOG_DEBUG, TAG, "Received new message: %s", buf);
            parser(buf);
            if (isWork != 1) break;
        }
        __android_log_print(ANDROID_LOG_DEBUG, TAG, "Close connection");
        close(sock);
        if (isWork != 1) break;
    }
    close(listener);
    return ERROR;
}

/**
 * the string has the form "firstName|lastName"
 */
int parser(char *buf) {
    char *separator = "|";
    char *firstName;
    char *lastName;

    firstName = strtok(buf, separator);
    lastName  = strtok(NULL, separator);
    insertToDb(firstName, lastName);

    return SUCCESSFULLY;
}

/**
 * destroy this server
 */
int destroyServer() {
    isWork = -1;
    __android_log_print(ANDROID_LOG_DEBUG, TAG, "Destroy server");
    return SUCCESSFULLY;
}

/**
 * Insert into the before created database.
 */
int insertToDb(char *firstName, char *lastName) {
    if (firstName && lastName) {
        insert(firstName, lastName);
        return SUCCESSFULLY;
    }
    return ERROR;
}


