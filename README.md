

This is  my solution for the code challenge described below about the two endpoints/sink interaction.
You have a Launcher class (a java main class) where objects are wired together and the
application can be setup and run.
Unfortunately I didn't have the time to use spring but it's not so bad.

The application has it's own domain based on an EventBus with just one thread
that feeds event consumer. There are two threads that read
from the two endpoints and produces event contained the read messages.
There's a repository (in memory) where id are stored and checked in order to understand whether
an id is joined (I assumed it could happen just one time).

Then there are different event consumer:

- one stores the messages and understands if it is a joined scenario
- one that waits to understand when producers are completed in order to send all the sink messages
for the orphaned
- one that compose and sends the sink messages

All the interfaces to the external world are build according to the ports/adapter paradigm.
I've kept all the still not joined.

I used maven to build the application and to produce a fat jar. I started with few integration
test but I was worried not to complete the test in time so I didn't write any other test.

If you build the application with maven you will find a fat jar in the target folder and you can
launch it just performing

> java -jar ./target/MessageSink-Processor.jar

It assumes the services are running as specified. You can change the host and the port in the
constructor of the clients, inside the Launcher class.

Coding Challenge
================
First notes about your program:
* It has to produce the right results.
* It shouldn't take more than about four hours.
* The code doesn't have to be perfect, but you need to be
  able to explain how things could be improved. (e.g., you
  could note that, "I'm not checking the return value here")
* You should be able to explain any choices or assumptions
  you made.

Problem Description
===================
We have supplied you with a small web server.  It is written in
Python. You can run it on any system with Python 2.6 or
Python 2.7.  Pretty much any Unix based system will work (e.g.
Linux or a Mac.)  You can probably even use a PC if you want,
but the verification tool may not work.

By default the web server listens on port 7299.

The web server has three endpoints:
  /source/a
  /source/b
  /sink/a

Source A emits JSON records.
Source B emits XML records.
Sink A accepts JSON records.

Most records from Source A will have a corresponding record
from Source B.  These are "joined" records.

Some records from one source will not have a match from the
other source. These are "orphaned" records.

Some records are malformed.  These are "defective" records.

Your program must read all the records from /source/a and
/source/b, categorize them as "joined", "orphaned", or "defective".
It must report the "joined' and "orphaned" records to /sink/a.  It
can ignore defective records.

By default the test program will emit around 1000 records. Once
all the records have been read from an endpoint it responds with
a "done" message.

In testing we will run your program against a much larger data
set, so your program should behave as if it is going to run forever.

Here's the catch.  The source and sink endpoints are interlinked.
Sometimes they will block until data has been read from or written
to the other sinks.  When this happens the request will return a
406 response.  They will never deadlock.


Testing
=======
The web server writes your responses and the expected response
into its running directory. We supply a program to compare the
these two files.


Message Specifications
======================

Endpoint /source/a
------------------
normal record: { "status": "ok", "id": "XXXXX" }
done record: {"status": "done"}

Endpoint /source/b
------------------
normal record:
<?xml version="1.0" encoding="UTF-8"?><msg><id value="$ID"/></msg>

done record:
<?xml version="1.0" encoding="UTF-8"?><msg><done/></msg>

Endpoint /sink/a
----------------
To endpoint in POST body:
{"kind": "$KIND", "id": "$ID"},
where $KIND can be either "joined" or "orphaned", and $ID is the $ID from the originating messages.

Success response:
{"status": "ok"}

Failure response:
{"status": "fail"}


How To Use The Tools
====================
* Execute the web server by running:
    python fixture.py
* The output will appear in the files out.txt and res.txt
  in the fixture's execution directory.
* Compare the out.txt and res.txt files by executing:
    bash check.sh
* For the comparison program, no output is good output

Submission
==========
Once you are done, please send your program along with instructions
for building and running it.  We will test it against a much larger
dataset.  We want a correct program in a short amount of time, rather
than a prefectly formulated program over a longer time.

You will find our code attached.  Good luck.

