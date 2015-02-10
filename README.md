thermospy
=========
Monitor a digital thermometer using a webcam and a Raspberry Pi

![Alt text](../docs/docs/gfx/thermospy.jpg "Thermospy in action")

Setup
========
  * Raspbian 2015-01-31
  * Raspberry PI 2 (also tested with Raspberry Model B+)
  * Microsoft Lifecam HD-3000
  * Digital Thermometer with a seven segment display
  * WiFi module TP-LINK TL-WN725N

How it works
============
The system is built as a client-server application. The server (Raspberry PI) is connected to the local WLAN using a WiFi module and is connected with a webcam. The server takes photos and tries to decode the image using an application called [ssocr](https://www.unix-ag.uni-kl.de/~auerswal/ssocr/). 
<br />
The client is implemented as an Android app and it communicates with the server over HTTP using a simple rest API. The rest services are defined by the server using Dropwizard and Jackson (for json).

When the user starts the application he/she needs to setup the camera so the server have something to parse. After the user enters IP & Port and connects to the server a simple wizard is started. At the first page the user needs to specify a boundary (around the display of the thermometer) to make life a bit simpler for the server. After the user confirms the boundary the server will return the parsed digits and ask the user to confirm. 

![Alt text](../docs/docs/gfx/setup.png "Server setup") ![Alt text](../docs/docs/gfx/bounds.png "Specify bounds") ![Alt text](../docs/docs/gfx/confirm.png "Confirm temperature")

Pre-requisites (if you want to run the binaries)
===============
  
## Build the server
  > 1. Java jdk8
  > 2. Maven
  
  
## Build the app
  > 1. Android Studio 1.0.2
  > 2. Android SDK matching your handset
  
Setup the server
================
 > $ sudo apt-get install fswebcam libimlib2 libx11-dev libimlib2-dev<br />
 > $ wget https://www.unix-ag.uni-kl.de/~auerswal/ssocr/ssocr-2.16.2.tar.bz2 ~/Downloads<br />
 > $ tar xfv ~/Downloads/ssocr-2.16.2.tar.bz2 && cd ~/Downloads/ssocr-2.16.2/ && make && sudo make install<br />
  
Make sure the webcam is working:<br />
 > $ fswebcam

Make sure ssocr is in the path:<br />
 > $ ssocr<br />

Get the source:<br />
>  $ mkdir ~/dev<br />
>  $ cd ~/dev<br />
>  $ git clone https://github.com/ludwigandersson/thermospy.git<br />

Build the server
>  $ cd ~/dev/thermospy/thermospy-server <br />
>  $ mvn package <br />

Start the server:
>  $ cd ~/dev/thermospy/thermospy-server/target <br />
>  $ java -jar thermospy-server-0.9.jar server ../thermospy-server.yml  <br />

Android App
===========
 1. Clone the repository
 2. Open Android studio.
 3. Open an existing Android Studio project and go to the repository.
   - Choose the "build.gradle" file within "thermospy-android".
 4. Make sure to use:
    - Use default gradle wrapper (not configured for the current project)
    - Project form: .idea (directory based)

Limitations
===========
  Not possible to decode decimals at the moment.<br />

Future improvements
===================
  * Support decimals
  * Support multiple cameras
  * Support multiple measurements from one picture
  * Support for a simple thermometer sensor 
  * Add db support to the server and log temperatures over time.
  * Use OpenCV or similar to fetch images from the webcam
  * Add support for auto-scan (i.e auto detect the Seven Segment display).






