thermospy
=========
Monitor a digital thermometer device using webcamera and Raspberry Pi.

Setup
========
Raspbian 2015-01-31
Raspberry PI 2 (also tested with Raspberry Model B+)
Microsoft Lifecam HD-3000
Digital Thermometer with a seven segment display
WiFi module TP-LINK TL-WN725N

Pre-requisites
===============
1. Make sure ”fswebcam” is working
2. Assign the PI an IP the phone can access using WiFi.

Software (Based on Raspbian)
============
$ Run rip-update, apt-get update, apt-get upgrade to make sure everything is up to date.
$ Make sure java jdk8 and Maven 3.2.5 (@see http://taoofmac.com/space/hw/RaspberryPi/JDK%20Installation) is installed.
$ sudo apt-get install fswebcam libimlib2 libx11-dev libimlib2-dev
$ wget https://www.unix-ag.uni-kl.de/~auerswal/ssocr/ssocr-2.16.2.tar.bz2 ~/Downloads
$ tar xfv ~/Downloads/ssocr-2.16.2.tar.bz2 && cd ~/Downloads/ssocr-2.16.2/ && make && sudo make install
Java, ant maven: https://taoofmac.com/space/hw/RaspberryPi/JDK%20Installation

Make sure the webcam is working, run:
$ fswebcam

Make sure ssocr is in the path, run:
$ ssocr

Get the source:
$ mkdir ~/dev
$ cd ~/dev
$ git clone https://github.com/ludwigandersson/thermospy.git

Build the server
$ cd ~/dev/thermospy/thermospy-server
$ mvn package

Start the server:
$ cd ~/dev/thermospy/thermospy-server/target
$ java -jar thermospy-server-0.9.jar server ../thermospy-server.yml 

Limitations
===========
Not possible to decode decimals at the moment.

Future improvements
===================
Lots of stuff...






