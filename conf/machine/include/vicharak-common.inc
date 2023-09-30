# Copyright (c) 2023 Vicharak Computers LLP
# Released under the MIT license (see COPYING.MIT for the terms)

# Do not use compressed kernel images
ROCKCHIP_KERNEL_COMPRESSED := "0"

# Enable debian package management
PACKAGE_CLASSES = "package_deb"
EXTRA_IMAGE_FEATURES += "package-management"

# Enable gnupg support
IMAGE_INSTALL:append = "gnupg"

MACHINE_EXTRA_RRECOMMENDS:append = " \
    rk-resize-helper \
"
