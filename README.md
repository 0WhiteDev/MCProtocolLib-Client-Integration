
# Minecraft ProtocolLib Client Integration ⚡

A Project integrating [Minecraft ProtocolLib](https://github.com/GeyserMC/MCProtocolLib) 1.8.8 for the Minecraft Client 💻

## Basic information

A project created for **Minecraft Clients 1.8.8 thanks to which you can integrate your client with everything McProtocolLib has to offer**, integration features include: 
- Connecting and manipulating multiple sessions at once 
- Sending custom packets using sessions 
- Connecting to the server using McProtocolLib from your own session
We use every possible type of auth such as **Mojang, Microsoft, TheAltening, EasyMc and basic non-premium bundling.**

This project is built on **basic Java 8** without any build tool like maven or gradle, which means that before starting the project using the IDE, you need to add the necessary libs, to do it follow the guide (This guide is based on Intellij):

- Go to File > Project Structure > Libraries
- Click "+" and "Java"
- Find a file called "lib" in the project file and add it
- Click "Apply"

## How to integrate the Client

To integrate the client, you will need to add a connection to the application (this project), I use the basic technology of telnet to connect.

In order for the client to be able to receive and send data to/from the McProtocolLib server, you must add classes to it that can be found in the **"Client Side Classes"** file, they are necessary to communicate with McProtocolLib.
(To build this I used my ClientBase which you can find here: [ClientBase SRC](https://github.com/0WhiteDev/Minecraft-Cheat-Client-Base))

To start a connection to the server, run this code (in your client source):
```
TelnetInit.init();
TelnetInit.getInstance().getOut().println(Channels.CLIENT);
```

In order for the connected sessions thanks to McProtocolLib to mimic our actions, such as walking and interacting with the environment, we need to add this code into NetworkManager in method sendPacket:

```
if (TelnetInit.getInstance().getOut() != null) {
    PacketsHandler.getInstance().handlePacket(packetIn);
}
```

So after editing the code, your sendPacket method should look something like this:

**NetworkManager.java**
```
public void sendPacket(Packet packetIn) {
    if (this.isChannelOpen()) {
        if (TelnetInit.getInstance().getOut() != null) {
            PacketsHandler.getInstance().handlePacket(packetIn);
        }
        this.flushOutboundQueue();
        this.dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[]) null);
    } else {
        this.readWriteLock.writeLock().lock();
        try {
            this.outboundPacketsQueue.add(new NetworkManager.InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener[]) null));
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }
}
```

**WARNING:** If you see any errors in your client, just do what your IDE tells you, in most cases changing the fields to public or adding getters should solve your problem.

To invoke the gui in which we can manipulate sessions, we need to use this code:

```
this.mc.displayGuiScreen(new GuiBotMenu());
```

For example, you can add a button to the GuiIngameMenu and when you click ESC in-game, you will have an additional button to initiate the opening of the gui:

**GuiIngameMenu.java > initGui()**
```
this.buttonList.add(new GuiButton(999, 10, 5, 98, 20, "McProtocolLib Connector"));
```
**GuiIngameMenu.java > actionPerformed(GuiButton button)**
```
case 999:
    this.mc.displayGuiScreen(new GuiBotMenu());
    break;
```

## Helpful tips

- Remember to turn on the applications before connecting from the client to McProtocolLib
- If for some reason you want to run applications on a different port or machine other than localhost, remember to change the port in the application and in the client

**Client Side:** (Here you can change the port and if you run applications on a machine other than the local one, pass here instead of 127.0.0.1, your server's IP)
```
// Class: TelnetInit Method: run()
Socket socket = new Socket("127.0.0.1", 8888);
```

**Server Side:** (Here you can change the port)
```
// Class: TelnetInit Method: init()
PORT = 8888;
```

## McProtocolLib

If you need to learn more about McProtocolLib, I encourage you to go to the website of the author of McProtocolLib, which [can be found here](https://github.com/GeyserMC/MCProtocolLib), where documentation and a description of the project are available.


## Project Suppot
If you need help, text to me:
- Discord: 0whtiedev / 0WhiteDev#0001
- Discord Server: https://discord.gg/KhExwvqZb5
- Email: 0whitedev@gmail.com
## Authors
Authors of the **MCProtocolLib Client Integration**:
- [@0WhiteDev](https://github.com/0WhiteDev)
- [@madeq](https://github.com/madeqq)
- [@DevsMarket](https://github.com/DEVS-MARKET)

Authors of the **McProtocolLib**:
- [@GeyserMC](https://github.com/GeyserMC)

