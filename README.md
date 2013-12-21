## `RcRun`, a simple init system for the Android OS

`RcRun` aims at starting services just like `/etc/init.d` would do. While some
_ROMs_ implement a real `init` system, many official _ROMs_ just don't allow the
user to start their own services. This is where `RcRun` enters the game.

Obviously, this software needs a _rooted_ ROM, such as [SuperSU](https://play.google.com/store/apps/details?id=eu.chainfire.supersu)
in order to start `root`-only services.

### Quickstart

Simply drop your scripts on a `rc.d` directory located in your primary `sdcard`,
possibly the internal one. `RcRun` will start every file located in that
directory following alphabetical order.

### TODO

Lots of stuff. Please be indulgent, this is actually my first ever Android app.
