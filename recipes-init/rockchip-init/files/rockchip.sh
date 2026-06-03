#!/bin/bash -e
# description: Description comes here....
CONSOLE_DEV=$(cat /sys/class/tty/console/active)
FIRST_BOOT_FILE="/usr/local/first-boot"


start() {
    # code to start app comes here
    # insert any kernel modules prior to 
    # executing/spawning any process that depends
    # on the LKM

    # If first boot file exists → do NOT run start logic
    if [ -f "$FIRST_BOOT_FILE" ]; then
        echo "First boot already completed. Skipping start." > /dev/"${CONSOLE_DEV}"
        return
    fi

   # CREATE first boot marker
    mkdir -p /usr/local
    touch "$FIRST_BOOT_FILE"
    
    /usr/sbin/rk-resize-helper start
    /usr/bin/u-boot-update
    echo "first-boot configured Successfully " > /dev/"${CONSOLE_DEV}"
}

stop() {
     echo "Stop service " > /dev/"${CONSOLE_DEV}"
    # code to stop app comes here 
    # example: killproc program_name
    # Kill all the process started in start() function
    # remove any LKM inserted using insmod in start()
}

case "$1" in 
    start)
       start
       ;;
    stop)
       stop
       ;;
    restart)
       stop
       start
       ;;
    status)
       # code to check status of app comes here 
       # example: status program_name
       ;;
    *)
       echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0 

# CHECKSUM: c8194b900507bdb8afd76d8d7635e04758674ecf36b484624361ba9db08fa456
