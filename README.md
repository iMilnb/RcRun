### `RcRun`, a simple init system for the Android OS

`RcRun` aims at starting services just like UNIX `init` would do. While some
_ROMs_ implement a real `init` system, many official _ROMs_ just don't allow the
user to start their own services. This is where `RcRun` enters the game.

Obviously, this software needs a _rooted_ ROM, embedding something like [SuperSU](https://play.google.com/store/apps/details?id=eu.chainfire.supersu)
in order to start `root`-only services.

#### Quickstart

Simply drop your scripts on a `rc.d` directory located in your primary `sdcard`,
possibly the internal one. `RcRun` will start every file located in that
directory following alphabetical order. I.e.

```
shell@jgedlte:/ $ ls /storage/sdcard0/rc.d/                                    
0dummy
99dropbear
shell@jgedlte:/ $ cat /storage/sdcard0/rc.d/99dropbear                         
#!/system/bin/sh

/data/dropbear/bin/dropbear -A -N shell -U 1000 -G 1000 -R /data/dropbear/etc/authorized_keys

```

#### Invoke `RcRun` manually

`RcRun` is a service called at boot time, or more precisely, when the
`BOOT_COMPLETED` broadcast message is received, so if you'd like to start the
service straight after installation, you'll have to send this message using
`adb`:

```
$ adb -s <device> shell am broadcast -a android.intent.action.BOOT_COMPLETED -c aandroid.intent.category.HOME -n net.imil.rcrun/.OnStartupRecv
```

When called the first time, `RcRun` will copy a template `rc` script to the
`rc.d` directory. From there, you could think of many complex scenarios.

#### TODO

Lots of stuff. Please be indulgent, this is actually my first ever Android app.
