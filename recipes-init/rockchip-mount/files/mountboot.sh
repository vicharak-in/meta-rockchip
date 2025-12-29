#!/bin/sh

set -e

__get_root_dev() {
        realpath "$(findmnt --nofsroot --noheadings --output SOURCE /)"
}

__get_block_dev() {
        echo "/dev/$(udevadm info --query=path "--name=$(__get_root_dev)" | awk -F'/' '{print $(NF-1)}')"
}

dev=$(__get_block_dev)

boot_part=$(parted -s "${dev}" print | grep -w "boot" | awk '{print $1}')

# Mount the boot partition
echo "Mounting boot partition  ${dev}p${boot_part} to /boot"
mount "${dev}p${boot_part}" /boot

mkdir -p /boot/overlays-$(uname -r)

echo "Mount operation completed successfully."
