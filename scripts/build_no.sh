#!/usr/bin/env bash

set -e

#####################################################################
#
# This script manages app build numbers.
# It returns the next build number to be used.
# The limit of size of the number is signed int, which is 2147483647.
#
# These numbers are used to mark app artifacts for:
# * Play Store - versionCode attribute (gradle)
# * Apple Store - CFBundleVersion attribute (plutil)
#
# For more details see:
# * https://developer.android.com/studio/publish/versioning
# * https://developer.apple.com/library/content/documentation/General/Reference/InfoPlistKeyReference/Articles/CoreFoundationKeys.html
#
# History:
#
# This script used to tag builds with `build-[0-9]+` tags.
# Since only release builds actually get uploaded to Play or Apple
# stores only then is the uniqueness of numbers checked.
# Because of that we are fine using just hour granurality.
#
#####################################################################


getNumber() {
    # Year + Month + Day + Hour
    date '+%y%m%d%H'
}

#####################################################################
# This should result in numbers like: 20181115 or 20181201

getNumber
