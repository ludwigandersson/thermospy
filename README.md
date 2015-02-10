thermospy
=========
Monitor a digital thermometer using a webcamera and a Raspberry Pi

![Alt text](/docs/gfx/thermospy.jpg "Thermospy in action")

Setup
========
  * Raspbian 2015-01-31
  * Raspberry PI 2 (also tested with Raspberry Model B+)
  * Microsoft Lifecam HD-3000
  * Digital Thermometer with a seven segment display
  * WiFi module TP-LINK TL-WN725N

Pre-requisites
===============
  1. Make sure the raspberry is on the same wifi network as the client.

Setup the server
================
  Run rip-update, apt-get update, apt-get upgrade to make sure everything is up to date.<br />
  Make sure java jdk8 and Maven 3.2.5 is installed.<br />
  $ sudo apt-get install fswebcam libimlib2 libx11-dev libimlib2-dev<br />
  $ wget https://www.unix-ag.uni-kl.de/~auerswal/ssocr/ssocr-2.16.2.tar.bz2 ~/Downloads<br />
  $ tar xfv ~/Downloads/ssocr-2.16.2.tar.bz2 && cd ~/Downloads/ssocr-2.16.2/ && make && sudo make install<br />
  
Make sure the webcam is working, run:<br />
  $ fswebcam

Make sure ssocr is in the path, run:<br />
  $ ssocr<br />

Get the source:<br />
  $ mkdir ~/dev<br />
  $ cd ~/dev<br />
  $ git clone https://github.com/ludwigandersson/thermospy.git<br />

Build the server
  $ cd ~/dev/thermospy/thermospy-server <br />
  $ mvn package <br />

Start the server:
  $ cd ~/dev/thermospy/thermospy-server/target <br />
  $ java -jar thermospy-server-0.9.jar server ../thermospy-server.yml  <br />

Android App
===========

Limitations
===========
  Not possible to decode decimals at the moment.<br />

Future improvements
===================
  Lots of stuff...<br />






